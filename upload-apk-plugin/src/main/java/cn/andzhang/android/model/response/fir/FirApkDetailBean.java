package cn.andzhang.android.model.response.fir;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * 名称	类型	说明
 * id	String	应用的id
 * type	String	ios 或者 android
 * name	String	app 名称
 * desc	String	app 详细描述
 * short	String	app 短链接
 * bundle_id	String	应用bundle_id
 * genre_id	Integer	类别 id
 * is_opened	Boolean	是否公开
 * has_combo	Boolean	是否有 combo app
 * is_show_plaza	Boolean	是否展示在广场页
 * created_at	Integer	创建时间(UTC 时间)
 * icon_url	Integer	icon的地址
 * is_owner	Boolean	是否是当前用户的 app
 * store_link_visible	Boolean	应用商店链接是否显示参数
 * passwd	Boolean	应用密码参数
 */
public class FirApkDetailBean implements Serializable {


    /**
     * id : 55559e625370693c78080000
     * type : android
     * name : 城桦怀_android_admin
     * desc :
     * short : wtam
     * is_opened : true
     * bundle_id : im.fir.xxx
     * is_show_plaza : true
     * passwd : null
     * max_release_count : 10
     * is_store_auto_sync : false
     * store_link_visible : false
     * genre_id : 0
     * created_at : 1431674466
     * has_combo : false
     * icon_url : null
     * is_owner : true
     */

    private String id;
    private String type;
    private String name;
    private String desc;
    @SerializedName("short")
    private String shortX;
    private boolean is_opened;
    private String bundle_id;
    private boolean is_show_plaza;
    private Object passwd;
    private int max_release_count;
    private boolean is_store_auto_sync;
    private boolean store_link_visible;
    private int genre_id;
    private int created_at;
    private boolean has_combo;
    private Object icon_url;
    private boolean is_owner;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getShortX() {
        return shortX;
    }

    public void setShortX(String shortX) {
        this.shortX = shortX;
    }

    public boolean isIs_opened() {
        return is_opened;
    }

    public void setIs_opened(boolean is_opened) {
        this.is_opened = is_opened;
    }

    public String getBundle_id() {
        return bundle_id;
    }

    public void setBundle_id(String bundle_id) {
        this.bundle_id = bundle_id;
    }

    public boolean isIs_show_plaza() {
        return is_show_plaza;
    }

    public void setIs_show_plaza(boolean is_show_plaza) {
        this.is_show_plaza = is_show_plaza;
    }

    public Object getPasswd() {
        return passwd;
    }

    public void setPasswd(Object passwd) {
        this.passwd = passwd;
    }

    public int getMax_release_count() {
        return max_release_count;
    }

    public void setMax_release_count(int max_release_count) {
        this.max_release_count = max_release_count;
    }

    public boolean isIs_store_auto_sync() {
        return is_store_auto_sync;
    }

    public void setIs_store_auto_sync(boolean is_store_auto_sync) {
        this.is_store_auto_sync = is_store_auto_sync;
    }

    public boolean isStore_link_visible() {
        return store_link_visible;
    }

    public void setStore_link_visible(boolean store_link_visible) {
        this.store_link_visible = store_link_visible;
    }

    public int getGenre_id() {
        return genre_id;
    }

    public void setGenre_id(int genre_id) {
        this.genre_id = genre_id;
    }

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }

    public boolean isHas_combo() {
        return has_combo;
    }

    public void setHas_combo(boolean has_combo) {
        this.has_combo = has_combo;
    }

    public Object getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(Object icon_url) {
        this.icon_url = icon_url;
    }

    public boolean isIs_owner() {
        return is_owner;
    }

    public void setIs_owner(boolean is_owner) {
        this.is_owner = is_owner;
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", shortX='" + shortX + '\'' +
                ", is_opened=" + is_opened +
                ", bundle_id='" + bundle_id + '\'' +
                ", is_show_plaza=" + is_show_plaza +
                ", passwd=" + passwd +
                ", max_release_count=" + max_release_count +
                ", is_store_auto_sync=" + is_store_auto_sync +
                ", store_link_visible=" + store_link_visible +
                ", genre_id=" + genre_id +
                ", created_at=" + created_at +
                ", has_combo=" + has_combo +
                ", icon_url=" + icon_url +
                ", is_owner=" + is_owner +
                '}';
    }
}
