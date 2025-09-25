
CREATE TABLE tasks_new (
    id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    notes TEXT,
    status VARCHAR(20) DEFAULT 'TODO',
    priority VARCHAR(10) DEFAULT 'MEDIUM',
    due_date TIMESTAMP,
    reminder_date TIMESTAMP,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

INSERT INTO tasks_new (id, user_id, title, description, notes, created_at, updated_at)
SELECT id, user_id, title, description, body, created_at, updated_at FROM articles;

DROP TABLE articles;
ALTER TABLE tasks_new RENAME TO tasks;

ALTER TABLE article_favorites RENAME TO important_tasks;
ALTER TABLE article_tags RENAME TO task_categories;
ALTER TABLE comments RENAME TO task_notes;
ALTER TABLE tags RENAME TO categories;

CREATE TABLE important_tasks_new (
    user_id VARCHAR(255) NOT NULL,
    task_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, task_id)
);

INSERT INTO important_tasks_new (user_id, task_id)
SELECT user_id, article_id FROM important_tasks;

DROP TABLE important_tasks;
ALTER TABLE important_tasks_new RENAME TO important_tasks;

CREATE TABLE task_categories_new (
    task_id VARCHAR(255) NOT NULL,
    category_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (task_id, category_id)
);

INSERT INTO task_categories_new (task_id, category_id)
SELECT article_id, tag_id FROM task_categories;

DROP TABLE task_categories;
ALTER TABLE task_categories_new RENAME TO task_categories;

CREATE TABLE task_notes_new (
    id VARCHAR(255) PRIMARY KEY,
    body TEXT NOT NULL,
    task_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

INSERT INTO task_notes_new (id, body, task_id, user_id, created_at)
SELECT id, body, article_id, user_id, created_at FROM task_notes;

DROP TABLE task_notes;
ALTER TABLE task_notes_new RENAME TO task_notes;
