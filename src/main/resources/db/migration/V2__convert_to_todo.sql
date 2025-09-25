ALTER TABLE articles RENAME TO tasks;
ALTER TABLE article_favorites RENAME TO important_tasks;
ALTER TABLE article_tags RENAME TO task_categories;
ALTER TABLE comments RENAME TO task_notes;
ALTER TABLE tags RENAME TO categories;

ALTER TABLE tasks ADD COLUMN status VARCHAR(20) DEFAULT 'TODO';
ALTER TABLE tasks ADD COLUMN priority VARCHAR(10) DEFAULT 'MEDIUM';
ALTER TABLE tasks ADD COLUMN due_date TIMESTAMP;
ALTER TABLE tasks ADD COLUMN reminder_date TIMESTAMP;
ALTER TABLE tasks RENAME COLUMN body TO notes;
ALTER TABLE tasks DROP COLUMN slug;

ALTER TABLE important_tasks RENAME COLUMN article_id TO task_id;
ALTER TABLE task_categories RENAME COLUMN article_id TO task_id;
ALTER TABLE task_categories RENAME COLUMN tag_id TO category_id;
ALTER TABLE task_notes RENAME COLUMN article_id TO task_id;

ALTER TABLE categories RENAME COLUMN name TO name;
