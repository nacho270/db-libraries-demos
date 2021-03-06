/*
 * This file is generated by jOOQ.
 */
package com.nacho.blog.jooq.jooqdemo.gen.tables;


import com.nacho.blog.jooq.jooqdemo.gen.Indexes;
import com.nacho.blog.jooq.jooqdemo.gen.Jooq;
import com.nacho.blog.jooq.jooqdemo.gen.Keys;
import com.nacho.blog.jooq.jooqdemo.gen.tables.records.TItemRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row4;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class TItem extends TableImpl<TItemRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>jooq.t_item</code>
     */
    public static final TItem T_ITEM = new TItem();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TItemRecord> getRecordType() {
        return TItemRecord.class;
    }

    /**
     * The column <code>jooq.t_item.id</code>.
     */
    public final TableField<TItemRecord, String> ID = createField(DSL.name("id"), SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>jooq.t_item.quantity</code>.
     */
    public final TableField<TItemRecord, Integer> QUANTITY = createField(DSL.name("quantity"), SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>jooq.t_item.f_product_id</code>.
     */
    public final TableField<TItemRecord, String> F_PRODUCT_ID = createField(DSL.name("f_product_id"), SQLDataType.VARCHAR(36).nullable(false), this, "");

    /**
     * The column <code>jooq.t_item.f_shipment_id</code>.
     */
    public final TableField<TItemRecord, String> F_SHIPMENT_ID = createField(DSL.name("f_shipment_id"), SQLDataType.VARCHAR(36).nullable(false), this, "");

    private TItem(Name alias, Table<TItemRecord> aliased) {
        this(alias, aliased, null);
    }

    private TItem(Name alias, Table<TItemRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>jooq.t_item</code> table reference
     */
    public TItem(String alias) {
        this(DSL.name(alias), T_ITEM);
    }

    /**
     * Create an aliased <code>jooq.t_item</code> table reference
     */
    public TItem(Name alias) {
        this(alias, T_ITEM);
    }

    /**
     * Create a <code>jooq.t_item</code> table reference
     */
    public TItem() {
        this(DSL.name("t_item"), null);
    }

    public <O extends Record> TItem(Table<O> child, ForeignKey<O, TItemRecord> key) {
        super(child, key, T_ITEM);
    }

    @Override
    public Schema getSchema() {
        return Jooq.JOOQ;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.T_ITEM_F_PRODUCT_ID, Indexes.T_ITEM_F_SHIPMENT_ID);
    }

    @Override
    public UniqueKey<TItemRecord> getPrimaryKey() {
        return Keys.KEY_T_ITEM_PRIMARY;
    }

    @Override
    public List<UniqueKey<TItemRecord>> getKeys() {
        return Arrays.<UniqueKey<TItemRecord>>asList(Keys.KEY_T_ITEM_PRIMARY);
    }

    @Override
    public List<ForeignKey<TItemRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<TItemRecord, ?>>asList(Keys.ITEM_IBFK_1, Keys.ITEM_IBFK_2);
    }

    private transient TProduct _tProduct;
    private transient TShipment _tShipment;

    public TProduct tProduct() {
        if (_tProduct == null)
            _tProduct = new TProduct(this, Keys.ITEM_IBFK_1);

        return _tProduct;
    }

    public TShipment tShipment() {
        if (_tShipment == null)
            _tShipment = new TShipment(this, Keys.ITEM_IBFK_2);

        return _tShipment;
    }

    @Override
    public TItem as(String alias) {
        return new TItem(DSL.name(alias), this);
    }

    @Override
    public TItem as(Name alias) {
        return new TItem(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public TItem rename(String name) {
        return new TItem(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public TItem rename(Name name) {
        return new TItem(name, null);
    }

    // -------------------------------------------------------------------------
    // Row4 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row4<String, Integer, String, String> fieldsRow() {
        return (Row4) super.fieldsRow();
    }
}
