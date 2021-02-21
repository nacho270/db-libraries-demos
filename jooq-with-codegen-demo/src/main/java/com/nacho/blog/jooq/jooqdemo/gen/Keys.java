/*
 * This file is generated by jOOQ.
 */
package com.nacho.blog.jooq.jooqdemo.gen;


import com.nacho.blog.jooq.jooqdemo.gen.tables.TItem;
import com.nacho.blog.jooq.jooqdemo.gen.tables.TProduct;
import com.nacho.blog.jooq.jooqdemo.gen.tables.TShipment;
import com.nacho.blog.jooq.jooqdemo.gen.tables.records.TItemRecord;
import com.nacho.blog.jooq.jooqdemo.gen.tables.records.TProductRecord;
import com.nacho.blog.jooq.jooqdemo.gen.tables.records.TShipmentRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * jooq.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<TItemRecord> KEY_T_ITEM_PRIMARY = Internal.createUniqueKey(TItem.T_ITEM, DSL.name("KEY_t_item_PRIMARY"), new TableField[] { TItem.T_ITEM.ID }, true);
    public static final UniqueKey<TProductRecord> KEY_T_PRODUCT_PRIMARY = Internal.createUniqueKey(TProduct.T_PRODUCT, DSL.name("KEY_t_product_PRIMARY"), new TableField[] { TProduct.T_PRODUCT.ID }, true);
    public static final UniqueKey<TShipmentRecord> KEY_T_SHIPMENT_PRIMARY = Internal.createUniqueKey(TShipment.T_SHIPMENT, DSL.name("KEY_t_shipment_PRIMARY"), new TableField[] { TShipment.T_SHIPMENT.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<TItemRecord, TProductRecord> ITEM_IBFK_1 = Internal.createForeignKey(TItem.T_ITEM, DSL.name("item_ibfk_1"), new TableField[] { TItem.T_ITEM.F_PRODUCT_ID }, Keys.KEY_T_PRODUCT_PRIMARY, new TableField[] { TProduct.T_PRODUCT.ID }, true);
    public static final ForeignKey<TItemRecord, TShipmentRecord> ITEM_IBFK_2 = Internal.createForeignKey(TItem.T_ITEM, DSL.name("item_ibfk_2"), new TableField[] { TItem.T_ITEM.F_SHIPMENT_ID }, Keys.KEY_T_SHIPMENT_PRIMARY, new TableField[] { TShipment.T_SHIPMENT.ID }, true);
}