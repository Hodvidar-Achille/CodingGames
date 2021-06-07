package com.hodvidar.miscellaneous.livecoding;

public class Solution2 {
    /**
     * select export.country as country, export.value as export, import.value as import
     * from(
     *     select c.country, COALESCE(SUM(t.value), 0) as value
     *     from trades t
     *     right outer join companies c on t.seller = c.name
     *     group by c.country
     * ) export
     * join
     * (
     *     select c.country,  COALESCE(SUM(t.value), 0) as value
     *     from trades t
     *     right outer join companies c on t.buyer = c.name
     *     group by c.country
     * ) import on export.country = import.country
     * order by export.country
     */
}
