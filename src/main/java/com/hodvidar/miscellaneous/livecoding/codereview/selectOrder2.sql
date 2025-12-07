SELECT
    o.order_id,
    o.amount,
    o.order_date,
    c.client_id,
    a.country,
    FLOOR(DATEDIFF(CURRENT_DATE, ci.birthdate) / 365.25) AS age
FROM orders AS o
         JOIN clientInfo AS ci   ON ci.client_id = o.client_id
         JOIN adresse    AS a    ON a.client_id = ci.client_id
WHERE a.country = 'FRANCE'
  AND FLOOR(DATEDIFF(CURRENT_DATE, ci.birthdate) / 365.25) BETWEEN 30 AND 45
  AND o.amount > 5000;