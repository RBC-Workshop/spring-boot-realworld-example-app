const taskForm = document.getElementById('task-form');
const taskList = document.querySelector('ul.divide-y');

const API_URL = '/api';
const TASKS_ENDPOINT = `${API_URL}/tasks`;
const CATEGORIES_ENDPOINT = `${API_URL}/categories`;

let tasks = [];
let categories = [];
let currentFilter = 'all';

function getAuthToken() {
    return localStorage.getItem('token');
}

function isAuthenticated() {
    return !!getAuthToken();
}

async function fetchTasks(filter = '') {
    if (!isAuthenticated()) {
        window.location.href = '/login.html';
        return;
    }

    try {
        let url = TASKS_ENDPOINT;
        if (filter) {
            url += `?${filter}`;
        }
        
        const response = await fetch(url, {
            headers: {
                'Authorization': `Token ${getAuthToken()}`
            }
        });
        
        if (response.ok) {
            const data = await response.json();
            tasks = data.tasks || [];
            renderTasks();
        } else {
            console.error('Failed to fetch tasks');
        }
    } catch (error) {
        console.error('Error fetching tasks:', error);
    }
}

async function fetchCategories() {
    try {
        const response = await fetch(CATEGORIES_ENDPOINT, {
            headers: {
                'Authorization': `Token ${getAuthToken()}`
            }
        });
        
        if (response.ok) {
            const data = await response.json();
            categories = data.categories || [];
            renderCategories();
        }
    } catch (error) {
        console.error('Error fetching categories:', error);
    }
}

async function createTask(taskData) {
    try {
        const response = await fetch(TASKS_ENDPOINT, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Token ${getAuthToken()}`
            },
            body: JSON.stringify({ task: taskData })
        });
        
        if (response.ok) {
            const data = await response.json();
            tasks.unshift(data.task);
            renderTasks();
            return true;
        }
        return false;
    } catch (error) {
        console.error('Error creating task:', error);
        return false;
    }
}

async function updateTaskStatus(taskId, completed) {
    try {
        const status = completed ? 'COMPLETED' : 'TODO';
        const response = await fetch(`${TASKS_ENDPOINT}/${taskId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Token ${getAuthToken()}`
            },
            body: JSON.stringify({ 
                task: { status } 
            })
        });
        
        if (response.ok) {
            const data = await response.json();
            const taskIndex = tasks.findIndex(t => t.id === taskId);
            if (taskIndex !== -1) {
                tasks[taskIndex] = data.task;
                renderTasks();
            }
            return true;
        }
        return false;
    } catch (error) {
        console.error('Error updating task:', error);
        return false;
    }
}

function renderTasks() {
    if (!taskList) return;
    
    taskList.innerHTML = '';
    
    if (tasks.length === 0) {
        taskList.innerHTML = `
            <li class="p-8 text-center text-gray-500">
                No tasks found. Add a new task to get started!
            </li>
        `;
        return;
    }
    
    tasks.forEach(task => {
        const isCompleted = task.status === 'COMPLETED';
        const priorityClass = `priority-${task.priority ? task.priority.toLowerCase() : 'medium'}`;
        
        const taskElement = document.createElement('li');
        taskElement.className = `p-4 hover:bg-gray-50 ${priorityClass}`;
        taskElement.innerHTML = `
            <div class="flex items-center justify-between">
                <div class="flex items-center space-x-3">
                    <input type="checkbox" class="h-5 w-5 text-blue-600 rounded focus:ring-blue-500" 
                           ${isCompleted ? 'checked' : ''} data-task-id="${task.id}">
                    <span class="text-lg ${isCompleted ? 'line-through text-gray-400' : ''}">${task.title}</span>
                </div>
                <div class="flex items-center space-x-2">
                    <span class="px-2 py-1 bg-${getPriorityColor(task.priority)} text-xs rounded-full">
                        ${task.priority || 'Medium'}
                    </span>
                    ${task.dueDate ? `<span class="text-gray-500 text-sm">${formatDate(task.dueDate)}</span>` : ''}
                    <button class="text-gray-400 hover:text-gray-600 task-menu" data-task-id="${task.id}">
                        <i class="fas fa-ellipsis-v"></i>
                    </button>
                </div>
            </div>
            ${task.description ? `
            <div class="ml-8 mt-2">
                <p class="${isCompleted ? 'text-gray-400 line-through' : 'text-gray-600'}">${task.description}</p>
                <div class="mt-2 flex items-center space-x-2">
                    ${renderCategories(task.categories)}
                    ${task.reminderDate ? `
                    <span class="text-gray-500 text-xs">
                        <i class="far fa-bell mr-1"></i> Reminder: ${formatTime(task.reminderDate)}
                    </span>
                    ` : ''}
                </div>
            </div>
            ` : ''}
        `;
        
        taskList.appendChild(taskElement);
    });
    
    document.querySelectorAll('input[type="checkbox"][data-task-id]').forEach(checkbox => {
        checkbox.addEventListener('change', handleTaskStatusChange);
    });
    
    document.querySelectorAll('.task-menu').forEach(button => {
        button.addEventListener('click', handleTaskMenuClick);
    });
}

function renderCategories(taskCategories = []) {
    if (!taskCategories || taskCategories.length === 0) return '';
    
    return taskCategories.map(category => `
        <span class="px-2 py-1 bg-blue-100 text-blue-800 text-xs rounded-full">${category}</span>
    `).join('');
}

function handleTaskStatusChange(event) {
    const taskId = event.target.dataset.taskId;
    const completed = event.target.checked;
    updateTaskStatus(taskId, completed);
}

function handleTaskMenuClick(event) {
    const taskId = event.currentTarget.dataset.taskId;
    console.log('Task menu clicked for task:', taskId);
}

function getPriorityColor(priority) {
    switch (priority) {
        case 'URGENT': return 'red-100 text-red-800';
        case 'HIGH': return 'orange-100 text-orange-800';
        case 'MEDIUM': return 'yellow-100 text-yellow-800';
        case 'LOW': return 'green-100 text-green-800';
        default: return 'blue-100 text-blue-800';
    }
}

function formatDate(dateString) {
    const date = new Date(dateString);
    const today = new Date();
    const tomorrow = new Date(today);
    tomorrow.setDate(tomorrow.getDate() + 1);
    
    if (date.toDateString() === today.toDateString()) {
        return 'Today';
    } else if (date.toDateString() === tomorrow.toDateString()) {
        return 'Tomorrow';
    } else {
        return date.toLocaleDateString();
    }
}

function formatTime(dateString) {
    const date = new Date(dateString);
    return date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

document.addEventListener('DOMContentLoaded', () => {
    if (isAuthenticated()) {
        fetchTasks();
        fetchCategories();
    } else {
        window.location.href = '/login.html';
    }
    
    if (taskForm) {
        taskForm.addEventListener('submit', (e) => {
            e.preventDefault();
            const formData = new FormData(taskForm);
            const taskData = {
                title: formData.get('title'),
                description: formData.get('description') || '',
                priority: formData.get('priority') || 'MEDIUM',
                categoryList: formData.get('category') ? [formData.get('category')] : [],
                dueDate: formData.get('dueDate') || null,
                reminderDate: formData.get('reminderDate') || null
            };
            
            createTask(taskData);
            taskForm.reset();
        });
    }
    
    document.querySelectorAll('aside a').forEach(link => {
        link.addEventListener('click', (e) => {
            e.preventDefault();
            const filterText = e.currentTarget.textContent.trim();
            
            document.querySelectorAll('aside a').forEach(a => {
                a.classList.remove('bg-blue-100', 'text-blue-700');
                a.classList.add('hover:bg-gray-100');
            });
            e.currentTarget.classList.add('bg-blue-100', 'text-blue-700');
            e.currentTarget.classList.remove('hover:bg-gray-100');
            
            let filterParam = '';
            switch (filterText) {
                case 'Today':
                    filterParam = 'dueBefore=tomorrow';
                    break;
                case 'Important':
                    filterParam = 'important=true';
                    break;
                case 'Upcoming':
                    filterParam = 'status=TODO';
                    break;
                case 'Completed':
                    filterParam = 'status=COMPLETED';
                    break;
                default:
                    filterParam = '';
            }
            
            fetchTasks(filterParam);
        });
    });
});
