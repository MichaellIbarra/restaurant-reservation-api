INSERT INTO districts (id, name)
VALUES ('1', 'Cercado de Lima'), ('2', 'Ate'), ('3', 'Barranco'), ('4', 'Breña'),
       ('5', 'Comas'), ('6', 'Chorrillos'), ('7', 'El Agustino'), ('8', 'Jesús María'), ('9', 'La Molina'), ('10', 'La Victoria');

INSERT INTO restaurants (id, address, capacity, name, phone_number, price_per_person, district_id) VALUES ('1','Av. ' ||
                                                                                                               'Abancay 123', '100', 'Restaurante Lima Centro', '01-1234567', '50', '1'), ('2', 'Jr. De La Unión 456', '80', 'Café El Centro', '01-2345678', '30', '1'), ('3', 'Av. Javier Prado Este 789', '150', 'Ate Sazón', '01-3456789', '45', '2'), ('4', 'Av. Metropolitana 101', '120', 'Sabor de Ate', '01-4567890', '35', '2'), ('5', 'Av. San Martín 127', '90', 'Barranco Bites', '01-5678901', '60', '3'), ('6', 'Av. Bolognesi 203', '50', 'Café Barranco', '01-6789012', '40', '3'), ('7', 'Av. Venezuela 304', '90', 'Breña Gourmet', '01-7890123', '25', '4'), ('8', 'Av. Varela 105', '110', 'Sazón de Breña', '01-8910234', '35', '4'), ('9', 'Av. Universitaria 506', '200', 'Comas Plaza', '01-9123456', '20', '5'), ('10', 'Av. Puno 607', '80', 'Huaylas Delicias', '01-0123456', '45', '6'), ('11', 'Av. Colina 809', '120', 'Mar de Chorrillos', '01-2345678', '35', '6'), ('12', 'Av. Riva Agüero 901', '150', 'El Agustino Sabor', '01-3456789', '30', '7'), ('13', 'Av. San Felipe 112', '100', 'Agustino Tradición', '01-4567890', '40', '7'), ('14', 'Av. Salaverry 111', '130', 'Jesús María Gourmet', '01-5678901', '50', '8'), ('15', 'Av. Huarochirí 232', '140', 'Delicias de Jesús María', '01-6789012', '45', '8'), ('16', 'Av. La Fontana 314', '180', 'La Molina Verde', '01-7890123', '55', '9'), ('17', 'Av. La Punta 415', '170', 'Sabores de la Molina', '01-8910234', '50', '9'), ('18', 'Av. Grau 516', '200', 'Victoria Cocina', '01-9123456', '40', '10'), ('19', 'Jr. Mendoza 617', '150', 'La Victoria Tradición', '01-0123457', '30', '10');

INSERT INTO users ("id", "email", "first_name", "last_name", "password", "role") VALUES ('1', '1mati@gmail
.com', '1Michaell Estiven', '1Ibarra', '$2a$10$I9jA6.Mv/h2L5U447G84pOYpWqguXtzxfeHqpLa0.mdLPG5xqh3Fq', 'USER'), ('2', 'mati@gmail.com', 'Michaell Estiven', 'Ibarra', '$2a$10$wrro0dl41F3QgHBh8bTKzuuF5je35yDa8k37GD1t5LqPzUFGmYTDO', 'USER'), ('3', 'michaell.ibarra@vallegrande.edu.pe', 'Estiven', 'Ibarra', '$2a$10$z.ZKWU4QhZX8Sqgks.waGeOvmLimgVZA.fj707MFx0MAq3WttOmhS', 'USER');


SHOW timezone;
SET timezone = 'America/lima'; -- Cambia a tu zona horaria deseada

SELECT * FROM reservations;