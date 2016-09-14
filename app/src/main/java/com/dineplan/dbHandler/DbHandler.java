package com.dineplan.dbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dineplan.model.Category;
import com.dineplan.model.Department;
import com.dineplan.model.MenuItem;
import com.dineplan.model.MenuPortion;
import com.dineplan.model.OrderTag;
import com.dineplan.model.OrderTagGroup;
import com.dineplan.model.PaymentType;
import com.dineplan.model.Syncer;
import com.dineplan.model.Tax;
import com.dineplan.model.TransactionType;

import java.util.ArrayList;

/**
 * Created by sandeepjoshi on 04/09/16.
 */
public class DbHandler extends SQLiteOpenHelper {


    public static final String DATABASE_NAME="dineplan";
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_TABLE01="syncer";
    public static final String DATABASE_TABLE02="category";
    public static final String DATABASE_TABLE03="menu_items";
    public static final String DATABASE_TABLE04="menu_portions";
    public static final String DATABASE_TABLE05="OrderTagGroups";
    public static final String DATABASE_TABLE06="orderTags";
    public static final String DATABASE_TABLE_PAYEMENT_TYPE="payment_type";
    public static final String DATABASE_TABLE_TRANSACTION_TYPE="transaction_type";
    public static final String DATABASE_TABLE_TAX="tax";
    public static final String DATABASE_TABLE_DEPARTMENT="department";
    private Context context;
    public DbHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SYNCER="CREATE TABLE "+DATABASE_TABLE01+" (id BIGINT UNIQUE, name TEXT, syncerGuid TEXT)";

        String CATEGORY="CREATE TABLE category (categoryId INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER, name TEXT)";
        String MENUITEM="CREATE TABLE menu_items (menuItemId INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER, name TEXT, aliasCode TEXT, aliasName TEXT, imagePath TEXT, description TEXT, isFavorite BOOLEAN, sortOrder INTEGER, barCode TEXT, categoryId INTEGER, price INTEGER DEFAULT (0))";
        String MENU_PORTION="CREATE TABLE menu_portions (portionId INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER, portionName TEXT, price TEXT, menuId INTEGER)";
        String TAGGROUPS="CREATE TABLE OrderTagGroups (tagGrpId INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER , name TEXT, minSelectItems INTEGER, maxSelectItems INTEGER, addPriceToOrder BOOLEAN, sortOrder INTEGER, categoryId INTEGER, menuItemId INTEGER)";
        String ORDERTAG="CREATE TABLE orderTags (orderTagId INTEGER PRIMARY KEY AUTOINCREMENT,id INTEGER , price INTEGER, name TEXT, sortOrder INTEGER, tagGroupId INTEGER)";
        String CREATE_TABLE_PAYMENT_TYPE="CREATE TABLE "+DATABASE_TABLE_PAYEMENT_TYPE+" " +
                "(id INTEGER ,name TEXT, acceptChange INTEGER, sortOrder INTEGER)";
        String CREATE_TABLE_TRANSACTION_TYPE="CREATE TABLE "+DATABASE_TABLE_TRANSACTION_TYPE+" " +
                "(id INTEGER ,name TEXT)";
        String CREATE_TABLE_TAX="CREATE TABLE "+DATABASE_TABLE_TAX+" " +
                "(id INTEGER ,name TEXT, percentage integer, categoryId integer, categoryName text, menuItemId integer, menuItemName text)";
        String CREATE_TABLE_DEPARTMENT="CREATE TABLE "+DATABASE_TABLE_DEPARTMENT+" " +
                "(id INTEGER ,name TEXT)";
        sqLiteDatabase.execSQL(SYNCER);
        sqLiteDatabase.execSQL(CATEGORY);
        sqLiteDatabase.execSQL(MENUITEM);
        sqLiteDatabase.execSQL(MENU_PORTION);
        sqLiteDatabase.execSQL(TAGGROUPS);
        sqLiteDatabase.execSQL(ORDERTAG);
        sqLiteDatabase.execSQL(CREATE_TABLE_PAYMENT_TYPE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TRANSACTION_TYPE);
        sqLiteDatabase.execSQL(CREATE_TABLE_TAX);
        sqLiteDatabase.execSQL(CREATE_TABLE_DEPARTMENT);
    }

    public void isSyncNeeded(ArrayList<Syncer> syncer){
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        for(Syncer sync:syncer) {
                Syncer sc=checkIfExist(sync);
                if(sc==null){
                    insertSyncer(sync);
                    sync.setSyncNeeded(true);
                }else
                if(!sc.getSyncerGuid().equals(sync.getSyncerGuid())){
                    sync.setSyncNeeded(true);
                    updateSyncer(sync);
                }

        }
    }


    private Syncer checkIfExist(Syncer sync) {
        Cursor mcursor = null;
        String selectQuery = "SELECT * FROM "+DATABASE_TABLE01+" where id="+sync.getId();
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        try {
            mcursor = myDataBase.rawQuery(selectQuery, null);
            if (mcursor.moveToFirst()) {
                    Syncer data = new Syncer();
                    data.setSyncerGuid(mcursor.getString(mcursor.getColumnIndex("syncerGuid")));
                    return data;
            }
                //myDataBase.close();
        } catch (Exception exp) {

        } finally {
            if (mcursor != null)
                mcursor.close();
        }
        return null;
    }

    private void updateSyncer(Syncer sync) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",sync.getName());
        values.put("syncerGuid",sync.getSyncerGuid());
        myDataBase.update(DATABASE_TABLE01, values,"id=?",new String[]{String.valueOf(sync.getId())});
    }


    private void insertSyncer(Syncer sync) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",sync.getId());
        values.put("name",sync.getName());
        values.put("syncerGuid",sync.getSyncerGuid());
        myDataBase.insert(DATABASE_TABLE01, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }



    public void SyncMenuCategories(ArrayList<Category> items) {
        removeData();

        SQLiteDatabase myDataBase = this.getReadableDatabase();
        for(Category categ:items){
            ContentValues values = new ContentValues();
            values.put("id",categ.getId());
            values.put("name",categ.getName());
           long myCateId=myDataBase.insert(DATABASE_TABLE02, null, values);
            if(categ.getMenuItems()!=null && categ.getMenuItems().size()!=0){
               insertMenuItems(categ.getMenuItems(),categ.getId());
            }
            if(categ.getOrderTagGroups()!=null && categ.getOrderTagGroups().size()!=0){
                insertOrderTagGroup(categ.getOrderTagGroups(),0,categ.getId());
            }
        }
    }

    private void removeData() {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        myDataBase.execSQL("delete from "+DATABASE_TABLE02);
        myDataBase.execSQL("delete from "+DATABASE_TABLE03);
        myDataBase.execSQL("delete from "+DATABASE_TABLE04);
        myDataBase.execSQL("delete from "+DATABASE_TABLE05);
        myDataBase.execSQL("delete from "+DATABASE_TABLE06);
    }


    private void insertMenuItems(ArrayList<MenuItem> menuItems,int categoryId) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        for(MenuItem menuItem:menuItems){
            ContentValues values = new ContentValues();
            values.put("id",menuItem.getId());
            values.put("name",menuItem.getName());
            values.put("aliasCode",menuItem.getAliasCode());
            values.put("aliasName",menuItem.getAliasName());
            values.put("imagePath",menuItem.getImagePath());
            values.put("description",menuItem.getDescription());
            values.put("isFavorite",menuItem.isFavorite());
            values.put("sortOrder",menuItem.getSortOrder());
            values.put("barCode",menuItem.getBarCode());
            values.put("categoryId",categoryId);
            if(menuItem.getMenuPortions()!=null && menuItem.getMenuPortions().size()!=0)
                values.put("price",menuItem.getMenuPortions().get(0).getPrice());
            long myMenu= myDataBase.insert(DATABASE_TABLE03, null, values);
            if(menuItem.getMenuPortions()!=null && menuItem.getMenuPortions().size()!=0){
                insertgetMenuPortions(menuItem.getMenuPortions(),menuItem.getId());
            }
            if(menuItem.getOrderTagGroups()!=null && menuItem.getOrderTagGroups().size()!=0){
                insertOrderTagGroup(menuItem.getOrderTagGroups(),menuItem.getId(),0);
            }
        }
    }

    private void insertOrderTagGroup(ArrayList<OrderTagGroup> orderTagGroups,int menuItemId,int categoryId) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        for(OrderTagGroup grp:orderTagGroups){
            ContentValues values = new ContentValues();
            values.put("id",grp.getId());
            values.put("name",grp.getName());
            values.put("minSelectItems",grp.getMinSelectItems());
            values.put("maxSelectItems",grp.getMaxSelectItems());
            values.put("addPriceToOrder",grp.isAddPriceToOrder());
            values.put("sortOrder",grp.getSortOrder());
            values.put("categoryId",categoryId);
            values.put("menuItemId",menuItemId);
            long myMenu= myDataBase.insert(DATABASE_TABLE05, null, values);
            if(grp.getOrderTags()!=null && grp.getOrderTags().size()!=0){
                insertOrderTag(grp.getOrderTags(),myMenu);
            }
          }
    }

    private void insertOrderTag(ArrayList<OrderTag> orderTags,long myMenu) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        for(OrderTag grp:orderTags){
            ContentValues values = new ContentValues();
            values.put("id",grp.getId());
            values.put("name",grp.getName());
            values.put("price",grp.getPrice());
            values.put("sortOrder",grp.getSortOrder());
            values.put("tagGroupId",myMenu);
            long id= myDataBase.insert(DATABASE_TABLE06, null, values);
        }
    }


    private void insertgetMenuPortions(ArrayList<MenuPortion> menuPortions, int menuId) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        for(MenuPortion portion:menuPortions){
            ContentValues values = new ContentValues();
            values.put("id",portion.getId());
            values.put("portionName",portion.getPortionName());
            values.put("price",portion.getPrice());
            values.put("menuId",menuId);
            long myPortion=myDataBase.insert(DATABASE_TABLE04, null, values);
        }
    }






    public ArrayList<MenuItem> getMenuItemList(){
        Cursor mcursor = null;
        ArrayList<MenuItem> menuItems=new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DATABASE_TABLE03+" order by name asc";
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        try {
            mcursor = myDataBase.rawQuery(selectQuery, null);
            if (mcursor.moveToFirst()) {
                do {
                    MenuItem data = new MenuItem();
                    data.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                    data.setName(mcursor.getString(mcursor.getColumnIndex("name")));
                    data.setAliasCode(mcursor.getString(mcursor.getColumnIndex("aliasCode")));
                    data.setAliasName(mcursor.getString(mcursor.getColumnIndex("aliasName")));
                    data.setImagePath(mcursor.getString(mcursor.getColumnIndex("imagePath")));
                    data.setDescription(mcursor.getString(mcursor.getColumnIndex("description")));
                    data.setFavorite(mcursor.getInt(mcursor.getColumnIndex("isFavorite"))==1);
                    data.setSortOrder(mcursor.getInt(mcursor.getColumnIndex("sortOrder")));
                    data.setBarCode(mcursor.getString(mcursor.getColumnIndex("barCode")));
                    data.setCategoryId(mcursor.getInt(mcursor.getColumnIndex("categoryId")));
                    data.setPrice(mcursor.getInt(mcursor.getColumnIndex("price")));
                    menuItems.add(data) ;

                }while (mcursor.moveToNext());
            }
            //myDataBase.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (mcursor != null)
                mcursor.close();
        }
        return menuItems;
    }

    public ArrayList<Category> getCategoryList() {
        Cursor mcursor = null;
        ArrayList<Category> menuItems=new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DATABASE_TABLE02;
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        try {
            mcursor = myDataBase.rawQuery(selectQuery, null);
            if (mcursor.moveToFirst()) {
                do {
                    Category data = new Category();
                    data.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                    data.setName(mcursor.getString(mcursor.getColumnIndex("name")));
                    menuItems.add(data) ;
                }while (mcursor.moveToNext());
            }
            //myDataBase.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (mcursor != null)
                mcursor.close();
        }
        return menuItems;
    }

    public MenuItem getMenuItemDetail(MenuItem menuItem) {
        Cursor mcursor = null;
        ArrayList<MenuPortion> menuItems=new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DATABASE_TABLE04+" where menuId="+menuItem.getId();
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        try {
            mcursor = myDataBase.rawQuery(selectQuery, null);
            if (mcursor.moveToFirst()) {
                do {
                    MenuPortion data = new MenuPortion();
                    data.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                    data.setPortionName(mcursor.getString(mcursor.getColumnIndex("portionName")));
                    data.setPrice(mcursor.getInt(mcursor.getColumnIndex("price")));
                    menuItems.add(data) ;
                }while (mcursor.moveToNext());
            }
            menuItem.setMenuPortions(menuItems);


            ArrayList<OrderTagGroup> orderTagGroups=new ArrayList<>();
            ArrayList<OrderTag> tags=null;
            selectQuery = "SELECT * FROM OrderTagGroups  where menuItemId="+menuItem.getId() +" or categoryId="+menuItem.getCategoryId();

                mcursor = myDataBase.rawQuery(selectQuery, null);
                if (mcursor.moveToFirst()) {
                    OrderTagGroup orderTagGroup=null;
                    do {

                                orderTagGroup=new OrderTagGroup();
                                orderTagGroup.setName(mcursor.getString(mcursor.getColumnIndex("name")));
                                orderTagGroup.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                                orderTagGroup.setAddPriceToOrder(mcursor.getInt(mcursor.getColumnIndex("addPriceToOrder")) == 1);
                                orderTagGroup.setMaxSelectItems(mcursor.getInt(mcursor.getColumnIndex("maxSelectItems")));
                                orderTagGroup.setMinSelectItems(mcursor.getInt(mcursor.getColumnIndex("minSelectItems")));
                                orderTagGroup.setSortOrder(mcursor.getInt(mcursor.getColumnIndex("sortOrder")));
                                orderTagGroup.setOrderTagId(mcursor.getInt(mcursor.getColumnIndex("tagGrpId")));
                                orderTagGroups.add(orderTagGroup);

                    }while (mcursor.moveToNext());
                }


            for(OrderTagGroup g:orderTagGroups) {
                selectQuery = "SELECT * FROM orderTags  where tagGroupId="+g.getOrderTagId();
                mcursor = myDataBase.rawQuery(selectQuery, null);
                if (mcursor.moveToFirst()) {
                    tags=new ArrayList<>();
                    OrderTagGroup orderTagGroup = null;
                    do {
                        OrderTag ordertag = new OrderTag();
                        ordertag.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                        ordertag.setName(mcursor.getString(mcursor.getColumnIndex("name")));
                        ordertag.setSortOrder(mcursor.getInt(mcursor.getColumnIndex("sortOrder")));
                        ordertag.setPrice(mcursor.getInt(mcursor.getColumnIndex("price")));
                        tags.add(ordertag);
                    } while (mcursor.moveToNext());
                    g.setOrderTags(tags);
                }


            }
            menuItem.setOrderTagGroups(orderTagGroups);


            //myDataBase.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (mcursor != null)
                mcursor.close();
        }
        return menuItem;
    }

    private void insertPaymentType(PaymentType paymentType) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", paymentType.getId());
        values.put("name", paymentType.getName());
        values.put("acceptChange", paymentType.isAcceptChange());
        values.put("sortOrder", paymentType.getSortOrder());
        myDataBase.insert(DATABASE_TABLE_PAYEMENT_TYPE, null, values);
    }

    public ArrayList<PaymentType> getPaymentTypeList() {
        Cursor mcursor = null;
        ArrayList<PaymentType> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DATABASE_TABLE_PAYEMENT_TYPE;
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        try {
            mcursor = myDataBase.rawQuery(selectQuery, null);
            if (mcursor.moveToFirst()) {
                do {
                    PaymentType data = new PaymentType();
                    data.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                    data.setSortOrder(mcursor.getInt(mcursor.getColumnIndex("sortOrder")));
                    data.setName(mcursor.getString(mcursor.getColumnIndex("name")));
                    data.setAcceptChange(mcursor.getInt(mcursor.getColumnIndex("acceptChange"))==0 ? false: true);
                    list.add(data) ;
                }while (mcursor.moveToNext());
            }
            //myDataBase.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (mcursor != null)
                mcursor.close();
        }
        return list;
    }

    private void clearPaymentTypeTable() {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        myDataBase.execSQL("delete from "+DATABASE_TABLE_PAYEMENT_TYPE);
    }

    public void syncPaymentType(ArrayList<PaymentType> list){

        try{
            clearPaymentTypeTable();

            for(PaymentType paymentType : list){
                insertPaymentType(paymentType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void insertTransactionType(TransactionType transactionType) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", transactionType.getId());
        values.put("name", transactionType.getName());
        myDataBase.insert(DATABASE_TABLE_TRANSACTION_TYPE, null, values);
    }

    public ArrayList<TransactionType> getTransactionTypeList() {
        Cursor mcursor = null;
        ArrayList<TransactionType> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DATABASE_TABLE_TRANSACTION_TYPE;
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        try {
            mcursor = myDataBase.rawQuery(selectQuery, null);
            if (mcursor.moveToFirst()) {
                do {
                    TransactionType data = new TransactionType();
                    data.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                    data.setName(mcursor.getString(mcursor.getColumnIndex("name")));
                    list.add(data) ;
                }while (mcursor.moveToNext());
            }
            //myDataBase.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (mcursor != null)
                mcursor.close();
        }
        return list;
    }

    private void clearTransactionTypeTable() {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        myDataBase.execSQL("delete from "+DATABASE_TABLE_TRANSACTION_TYPE);
    }

    public void syncTransactionType(ArrayList<TransactionType> list){

        try{
            clearTransactionTypeTable();

            for(TransactionType transactionType : list){
                insertTransactionType(transactionType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void insertTax(Tax tax) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", tax.getId());
        values.put("name", tax.getName());
        values.put("percentage", tax.getPercentage());
        values.put("categoryId", tax.getCategoryId());
        values.put("categoryName", tax.getCategoryName());
        values.put("menuItemId", tax.getMenuItemId());
        values.put("menuItemName", tax.getMenuItemName());
        myDataBase.insert(DATABASE_TABLE_TAX, null, values);
    }

    public ArrayList<Tax> getTaxList() {
        Cursor mcursor = null;
        ArrayList<Tax> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DATABASE_TABLE_TAX;
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        try {
            mcursor = myDataBase.rawQuery(selectQuery, null);
            if (mcursor.moveToFirst()) {
                do {
                    Tax data = new Tax();
                    data.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                    data.setName(mcursor.getString(mcursor.getColumnIndex("name")));
                    data.setPercentage(mcursor.getInt(mcursor.getColumnIndex("percentage")));
                    data.setCategoryId(mcursor.getInt(mcursor.getColumnIndex("categoryId")));
                    data.setCategoryName(mcursor.getString(mcursor.getColumnIndex("categoryName")));
                    data.setMenuItemId(mcursor.getInt(mcursor.getColumnIndex("menuItemId")));
                    data.setMenuItemName(mcursor.getString(mcursor.getColumnIndex("menuItemName")));
                    list.add(data) ;
                }while (mcursor.moveToNext());
            }
            //myDataBase.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (mcursor != null)
                mcursor.close();
        }
        return list;
    }

    private void clearTaxTable() {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        myDataBase.execSQL("delete from "+DATABASE_TABLE_TAX);
    }

    public void syncTaxType(ArrayList<Tax> list){

        try{
            clearTransactionTypeTable();

            for(Tax tax : list){
                insertTax(tax);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void insertDepartment(Department department) {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", department.getId());
        values.put("name", department.getName());
        myDataBase.insert(DATABASE_TABLE_DEPARTMENT, null, values);
    }

    public ArrayList<Department> getDepartmentList() {
        Cursor mcursor = null;
        ArrayList<Department> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM "+DATABASE_TABLE_DEPARTMENT;
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        try {
            mcursor = myDataBase.rawQuery(selectQuery, null);
            if (mcursor.moveToFirst()) {
                do {
                    Department data = new Department();
                    data.setId(mcursor.getInt(mcursor.getColumnIndex("id")));
                    data.setName(mcursor.getString(mcursor.getColumnIndex("name")));
                    list.add(data) ;
                }while (mcursor.moveToNext());
            }
            //myDataBase.close();
        } catch (Exception exp) {
            exp.printStackTrace();
        } finally {
            if (mcursor != null)
                mcursor.close();
        }
        return list;
    }

    private void clearDepartmentTable() {
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        myDataBase.execSQL("delete from "+DATABASE_TABLE_DEPARTMENT);
    }

    public void syncDepartment(ArrayList<Department> list){

        try{
            clearTransactionTypeTable();

            for(Department department : list){
                insertDepartment(department);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateSyncRequire(int id){
        try{
            SQLiteDatabase myDataBase = this.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put("syncerGuid", "");
            myDataBase.update(DATABASE_TABLE01, values,"id=?",new String[]{String.valueOf(id)});
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
