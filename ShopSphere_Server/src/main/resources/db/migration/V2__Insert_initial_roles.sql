-- ================== Insert Initial Roles ==================
INSERT INTO roles (name, description) VALUES
('SUPER_ADMIN', 'Quản lý toàn bộ hệ thống, có quyền cao nhất'),
('ADMIN', 'Quản lý sản phẩm, đơn hàng, khách hàng'),
('MANAGER', 'Quản lý inventory, promotions, shipping'),
('STAFF', 'Xử lý đơn hàng, hỗ trợ khách hàng'),
('CUSTOMER', 'Khách hàng đã đăng ký'),
('GUEST', 'Khách hàng chưa đăng ký');

-- ================== Insert Initial Permissions ==================
INSERT INTO permissions (code, description) VALUES
-- User Management
('USER_CREATE', 'Tạo user mới'),
('USER_READ', 'Xem thông tin user'),
('USER_UPDATE', 'Cập nhật thông tin user'),
('USER_DELETE', 'Xóa user'),
('USER_LIST', 'Xem danh sách users'),

-- Product Management
('PRODUCT_CREATE', 'Tạo sản phẩm mới'),
('PRODUCT_READ', 'Xem thông tin sản phẩm'),
('PRODUCT_UPDATE', 'Cập nhật sản phẩm'),
('PRODUCT_DELETE', 'Xóa sản phẩm'),
('PRODUCT_LIST', 'Xem danh sách sản phẩm'),

-- Category Management
('CATEGORY_CREATE', 'Tạo danh mục mới'),
('CATEGORY_READ', 'Xem thông tin danh mục'),
('CATEGORY_UPDATE', 'Cập nhật danh mục'),
('CATEGORY_DELETE', 'Xóa danh mục'),
('CATEGORY_LIST', 'Xem danh sách danh mục'),

-- Order Management
('ORDER_CREATE', 'Tạo đơn hàng'),
('ORDER_READ', 'Xem thông tin đơn hàng'),
('ORDER_UPDATE', 'Cập nhật đơn hàng'),
('ORDER_DELETE', 'Xóa đơn hàng'),
('ORDER_LIST', 'Xem danh sách đơn hàng'),
('ORDER_PROCESS', 'Xử lý đơn hàng'),

-- Customer Management
('CUSTOMER_CREATE', 'Tạo khách hàng'),
('CUSTOMER_READ', 'Xem thông tin khách hàng'),
('CUSTOMER_UPDATE', 'Cập nhật khách hàng'),
('CUSTOMER_DELETE', 'Xóa khách hàng'),
('CUSTOMER_LIST', 'Xem danh sách khách hàng'),

-- Inventory Management
('INVENTORY_READ', 'Xem thông tin inventory'),
('INVENTORY_UPDATE', 'Cập nhật inventory'),
('INVENTORY_LIST', 'Xem danh sách inventory'),

-- Promotion Management
('PROMOTION_CREATE', 'Tạo promotion'),
('PROMOTION_READ', 'Xem thông tin promotion'),
('PROMOTION_UPDATE', 'Cập nhật promotion'),
('PROMOTION_DELETE', 'Xóa promotion'),
('PROMOTION_LIST', 'Xem danh sách promotion'),

-- Report Management
('REPORT_SALES', 'Xem báo cáo doanh số'),
('REPORT_INVENTORY', 'Xem báo cáo inventory'),
('REPORT_CUSTOMER', 'Xem báo cáo khách hàng'),
('REPORT_ORDER', 'Xem báo cáo đơn hàng'),

-- System Management
('SYSTEM_CONFIG', 'Cấu hình hệ thống'),
('SYSTEM_BACKUP', 'Backup hệ thống'),
('SYSTEM_LOG', 'Xem system logs');

-- ================== Assign Permissions to Roles ==================
-- SUPER_ADMIN gets all permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 1, id FROM permissions;

-- ADMIN permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, id FROM permissions 
WHERE code IN (
    'USER_READ', 'USER_LIST',
    'PRODUCT_CREATE', 'PRODUCT_READ', 'PRODUCT_UPDATE', 'PRODUCT_DELETE', 'PRODUCT_LIST',
    'CATEGORY_CREATE', 'CATEGORY_READ', 'CATEGORY_UPDATE', 'CATEGORY_DELETE', 'CATEGORY_LIST',
    'ORDER_READ', 'ORDER_UPDATE', 'ORDER_LIST', 'ORDER_PROCESS',
    'CUSTOMER_READ', 'CUSTOMER_UPDATE', 'CUSTOMER_LIST',
    'INVENTORY_READ', 'INVENTORY_UPDATE', 'INVENTORY_LIST',
    'PROMOTION_CREATE', 'PROMOTION_READ', 'PROMOTION_UPDATE', 'PROMOTION_DELETE', 'PROMOTION_LIST',
    'REPORT_SALES', 'REPORT_INVENTORY', 'REPORT_CUSTOMER', 'REPORT_ORDER'
);

-- MANAGER permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, id FROM permissions 
WHERE code IN (
    'PRODUCT_READ', 'PRODUCT_LIST',
    'ORDER_READ', 'ORDER_UPDATE', 'ORDER_LIST', 'ORDER_PROCESS',
    'CUSTOMER_READ', 'CUSTOMER_LIST',
    'INVENTORY_READ', 'INVENTORY_UPDATE', 'INVENTORY_LIST',
    'PROMOTION_CREATE', 'PROMOTION_READ', 'PROMOTION_UPDATE', 'PROMOTION_LIST',
    'REPORT_SALES', 'REPORT_INVENTORY'
);

-- STAFF permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, id FROM permissions 
WHERE code IN (
    'PRODUCT_READ', 'PRODUCT_LIST',
    'ORDER_READ', 'ORDER_UPDATE', 'ORDER_LIST', 'ORDER_PROCESS',
    'CUSTOMER_READ', 'CUSTOMER_LIST'
);

-- CUSTOMER permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 5, id FROM permissions 
WHERE code IN (
    'PRODUCT_READ', 'PRODUCT_LIST',
    'ORDER_CREATE', 'ORDER_READ', 'ORDER_LIST',
    'CUSTOMER_READ', 'CUSTOMER_UPDATE'
);

-- GUEST permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 6, id FROM permissions 
WHERE code IN (
    'PRODUCT_READ', 'PRODUCT_LIST'
);
