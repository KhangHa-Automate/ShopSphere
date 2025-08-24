-- Add created_by column to user_roles table
ALTER TABLE user_roles ADD COLUMN created_by UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE;

-- Add index for better performance
CREATE INDEX idx_user_roles_created_by ON user_roles(created_by);
