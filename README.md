# Java_SPRING_demo

    Seguindo curso Nélio Alves.
    Front bem simples criado para testar consumo via web.


Resetar banco:
    -- Disable foreign key checks
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE tb_audit_logs;
DROP TABLE tb_category;
DROP TABLE tb_clients;
DROP TABLE tb_order;
DROP TABLE tb_order_item;
DROP TABLE tb_payment;
DROP TABLE tb_permissions;
DROP TABLE tb_product;
DROP TABLE tb_product_category;
DROP TABLE tb_role;
DROP TABLE tb_role_permission_summary;
DROP TABLE tb_role_permissions;
DROP TABLE tb_users;

UPDATE server_config
	SET config_value = ''
    WHERE config_key = 'Seeded_db';
    
SELECT *
	FROM server_config;

-- Re-enable foreign key checks
SET FOREIGN_KEY_CHECKS = 1;
