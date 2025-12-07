-- We want order_id, amount, order_date, client_id and country
SELECT
    o.*,
    ci.*,
    a.*
FROM orders      o,
     clientInfos  ci,
     adresses     a
WHERE o.client_id = ci.client_id
  AND ci.client_id = a.client_id
  AND a.country = 'FRANCE'
  AND (YEAR(CURDATE()) - YEAR(ci.birthdate)) BETWEEN 30 AND 45
  AND o.amount > 5000;