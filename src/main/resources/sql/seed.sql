USE atelier_shop;

INSERT INTO category (id, name, icon, description) VALUES
(1, '数码科技', '📱', '手机、耳机、电脑，反正买新不买旧'),
(2, '时尚服饰', '👗', '衣柜里总缺那么一件'),
(3, '家居生活', '🏠', '住得舒服比什么都重要'),
(4, '美妆护肤', '✨', '对自己好一点，不贵'),
(5, '运动户外', '🏃', '买装备≈练过了'),
(6, '图书文具', '📚', '看书和买书是两回事');

INSERT INTO good (id, name, price, category_id, img, description, stock, status, hot) VALUES
(1, '无线降噪耳机 Pro', 1299.00, 1, '/img.png', '主动降噪，40小时续航，Hi-Fi 音质，舒适佩戴体验。', 50, 'on', 1),
(2, '智能手表 Ultra', 2499.00, 1, '/img_1.png', '健康监测、GPS 定位、50米防水，全天候智能伴侣。', 30, 'on', 1),
(3, '极简羊毛大衣', 1899.00, 2, '/img_2.png', '100% 澳洲美利奴羊毛，经典剪裁，温暖有型。', 20, 'on', 1),
(4, '真皮手提包', 1599.00, 2, '/img_4.png', '头层牛皮，手工缝制，大容量设计，商务通勤皆宜。', 15, 'on', 0),
(5, '北欧风台灯', 399.00, 3, '/img_3.png', '三档调光，护眼无频闪，简约设计点缀居家空间。', 80, 'on', 1),
(6, '记忆棉枕头', 299.00, 3, '/img_5.png', '人体工学曲线，慢回弹材质，深度睡眠从此开始。', 100, 'on', 0),
(7, '精华护肤套装', 899.00, 4, '/img_6.png', '三重玻尿酸配方，深层补水，焕活肌肤光泽。', 40, 'on', 1),
(8, '专业跑鞋', 799.00, 5, '/img_7.png', '碳板科技，轻量回弹，马拉松级缓震保护。', 60, 'on', 0),
(9, '露营帐篷 4人', 1299.00, 5, '/img_8.png', '防雨防风，快速搭建，户外露营的理想选择。', 25, 'on', 0),
(10, '设计思维经典', 68.00, 6, '/img_9.png', 'IDEO 设计方法论经典之作，激发创意灵感。', 200, 'on', 0),
(11, '机械键盘 RGB', 599.00, 1, '/img_10.png', '青轴手感，全键无冲，1680万色 RGB 灯效。', 45, 'off', 0),
(12, '真丝围巾', 459.00, 2, '/img_7.png', '100% 桑蚕丝，手工印花，四季百搭单品。', 35, 'on', 0);

INSERT INTO user (id, username, email, password, nickname, phone, address) VALUES
(1, 'demo', 'demo@shop.com', '$2b$12$R2COBlzOXhLcIxiFflAm4OT1C7D/ziV5ACwLrcUW2nHjDBEHeJVF.', '演示用户', '13800138000', '北京市朝阳区建国路 88 号'),
(2, 'test', 'test@shop.com', '$2b$12$R2COBlzOXhLcIxiFflAm4OT1C7D/ziV5ACwLrcUW2nHjDBEHeJVF.', '测试用户', '13900139000', '上海市浦东新区陆家嘴环路 1000 号');

INSERT INTO admin (id, username, password, role, name) VALUES
(1, 'admin', '$2b$12$qSsIpEzyRTrj09XjmW8U2OSZrVtXQMz41KDd.GjSnn/2aZ3AnYzLm', 'admin', '系统管理员'),
(2, 'operator', '$2b$12$rbXpgSmR4c/Y2IVtae/7NOHLq96X08NyCyz8gHTyri8XUyYFZg9/y', 'operator', '运营专员');

INSERT INTO shop_order (id, user_id, order_no, create_time, pay_time, status, total_price, address, receiver, phone, logistics_company, tracking_no, logistics_status) VALUES
(1, 1, '202601010001', '2026-01-01 10:00:00', '2026-01-01 10:05:00', 3, 1299.00, '北京市朝阳区建国路 88 号', '演示用户', '13800138000', '顺丰速运', 'SF1234567890', '已签收');

INSERT INTO order_item (order_id, good_id, count, price) VALUES
(1, 1, 1, 1299.00);

INSERT INTO carousel (id, title, subtitle, img, link, sort_order) VALUES
(1, '春季新品上市', '精选好物，限时优惠', 'https://picsum.photos/seed/banner1/1400/500', '/category/2', 1),
(2, '数码科技节', '智能设备低至 7 折', 'https://picsum.photos/seed/banner2/1400/500', '/category/1', 2),
(3, '品质家居', '打造理想生活空间', 'https://picsum.photos/seed/banner3/1400/500', '/category/3', 3),
(4, '运动户外季', '探索自然，释放活力', 'https://picsum.photos/seed/banner4/1400/500', '/category/5', 4);
