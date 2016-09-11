package com.dineplan.dbHandler;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dineplan.model.Category;
import com.dineplan.model.MenuItem;
import com.dineplan.model.MenuPortion;
import com.dineplan.model.OrderTag;
import com.dineplan.model.OrderTagGroup;
import com.dineplan.model.Syncer;

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
        sqLiteDatabase.execSQL(SYNCER);
        sqLiteDatabase.execSQL(CATEGORY);
        sqLiteDatabase.execSQL(MENUITEM);
        sqLiteDatabase.execSQL(MENU_PORTION);
        sqLiteDatabase.execSQL(TAGGROUPS);
        sqLiteDatabase.execSQL(ORDERTAG);
    }

    public void isSyncNeeded(ArrayList<Syncer> syncer){
        SQLiteDatabase myDataBase = this.getReadableDatabase();
        for(Syncer sync:syncer) {
                Syncer sc=checkIfExist(sync);
                if(sc==null){
                    insertSyncer(sync);
                    sync.setSyncNeeded(true);
                }else
                if(!sc.getSyncerGuid().equals(sc.getSyncerGuid())){
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
}
