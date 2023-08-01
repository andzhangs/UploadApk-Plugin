package cn.andzhang.android.model.response.fir;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * 查看应用列表信息
 */
public class FirApplicationBean implements Serializable {


    /**
     * apps_count : 3
     * page_size : 20
     * items : [{"id":"64c776bdf945487f734d8379","user_id":"59ae6ed67e4ff04bb9000007","org_id":"59ae6ed6548b7a04fe7b12ba","type":"android","name":"UploadApk-pgy-fir-Gradle","short":"74ntcr","bundle_id":"com.uplaod.apk","genre_id":0,"is_opened":true,"close_downloaded_ad":false,"download_category":"normal","download_domain":"fir.andzhang.cn","web_template":"default","custom_market_url":"","authen_status":"init","has_combo":false,"created_at":1690793661,"updated_at":1690800311,"expired_at":0,"stale_at":1722422711,"is_owner":true,"download_domain_https_ready":false,"show_refresh_stale_at":false,"icon_url":"https://pro-icon-qn.appc01.com/24acb32d583c349e875ed02fb1151130072f41e0?attname=icon_style_1.png&tmp=1690800311.357427","master_release":{"version":"1.0.0","build":"100","release_type":"","distribution_name":"","supported_platform":null,"created_at":1690800310}},{"id":"640979ef23389f1b5bb8b3ee","user_id":"59ae6ed67e4ff04bb9000007","org_id":"59ae6ed6548b7a04fe7b12ba","type":"android","name":"Bys-Android","short":"u8b7","bundle_id":"zs.attrsense.bys","genre_id":0,"is_opened":true,"close_downloaded_ad":false,"download_category":"normal","download_domain":"fir.andzhang.cn","web_template":"default","custom_market_url":"","authen_status":"init","has_combo":false,"created_at":1678342639,"updated_at":1684406969,"expired_at":0,"stale_at":1716532766,"is_owner":true,"download_domain_https_ready":false,"show_refresh_stale_at":false,"icon_url":"https://fir-app-icon.appc01.com/47f4977d277d4af46886d4bc10cfbd05317cbbb5?auth_key=1722422711-0-0-b7a2c5a0f529fa816c2df1632fe04090&tmp=1690800311.3584225","master_release":{"version":"1.0.0_alpha16","build":"1","release_type":"inhouse","distribution_name":null,"supported_platform":null,"created_at":1684406969}},{"id":"63562d33f945482794a15c0e","user_id":"59ae6ed67e4ff04bb9000007","org_id":"59ae6ed6548b7a04fe7b12ba","type":"android","name":"双深","short":"9d71","bundle_id":"com.attrsense.android","genre_id":0,"is_opened":true,"close_downloaded_ad":false,"download_category":"normal","download_domain":"fir.andzhang.cn","web_template":"default","custom_market_url":"","authen_status":"init","has_combo":false,"created_at":1666592051,"updated_at":1673588696,"expired_at":0,"stale_at":1713495154,"is_owner":true,"download_domain_https_ready":false,"show_refresh_stale_at":false,"icon_url":"https://fir-app-icon.appc01.com/9727ceba67088d8fc36da7f46dff4565c3a27483?auth_key=1722422711-0-0-78385262085a0cc301f53b7aae645844&tmp=1690800311.3592694","master_release":{"version":"1.1.0_alpha01","build":"4","release_type":"inhouse","distribution_name":null,"supported_platform":null,"created_at":1673588696}}]
     */

    private int apps_count;
    private int page_size;
    private List<ItemsBean> items;

    public int getApps_count() {
        return apps_count;
    }

    public void setApps_count(int apps_count) {
        this.apps_count = apps_count;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean implements Serializable{
        /**
         * id : 64c776bdf945487f734d8379
         * user_id : 59ae6ed67e4ff04bb9000007
         * org_id : 59ae6ed6548b7a04fe7b12ba
         * type : android
         * name : UploadApk-pgy-fir-Gradle
         * short : 74ntcr
         * bundle_id : com.uplaod.apk
         * genre_id : 0
         * is_opened : true
         * close_downloaded_ad : false
         * download_category : normal
         * download_domain : fir.andzhang.cn
         * web_template : default
         * custom_market_url :
         * authen_status : init
         * has_combo : false
         * created_at : 1690793661
         * updated_at : 1690800311
         * expired_at : 0
         * stale_at : 1722422711
         * is_owner : true
         * download_domain_https_ready : false
         * show_refresh_stale_at : false
         * icon_url : https://pro-icon-qn.appc01.com/24acb32d583c349e875ed02fb1151130072f41e0?attname=icon_style_1.png&tmp=1690800311.357427
         * master_release : {"version":"1.0.0","build":"100","release_type":"","distribution_name":"","supported_platform":null,"created_at":1690800310}
         */

        private String id;
        private String user_id;
        private String org_id;
        private String type;
        private String name;
        @SerializedName("short")
        private String shortX;
        private String bundle_id;
        private int genre_id;
        private boolean is_opened;
        private boolean close_downloaded_ad;
        private String download_category;
        private String download_domain;
        private String web_template;
        private String custom_market_url;
        private String authen_status;
        private boolean has_combo;
        private int created_at;
        private int updated_at;
        private int expired_at;
        private int stale_at;
        private boolean is_owner;
        private boolean download_domain_https_ready;
        private boolean show_refresh_stale_at;
        private String icon_url;
        private MasterReleaseBean master_release;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOrg_id() {
            return org_id;
        }

        public void setOrg_id(String org_id) {
            this.org_id = org_id;
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

        public String getShortX() {
            return shortX;
        }

        public void setShortX(String shortX) {
            this.shortX = shortX;
        }

        public String getBundle_id() {
            return bundle_id;
        }

        public void setBundle_id(String bundle_id) {
            this.bundle_id = bundle_id;
        }

        public int getGenre_id() {
            return genre_id;
        }

        public void setGenre_id(int genre_id) {
            this.genre_id = genre_id;
        }

        public boolean isIs_opened() {
            return is_opened;
        }

        public void setIs_opened(boolean is_opened) {
            this.is_opened = is_opened;
        }

        public boolean isClose_downloaded_ad() {
            return close_downloaded_ad;
        }

        public void setClose_downloaded_ad(boolean close_downloaded_ad) {
            this.close_downloaded_ad = close_downloaded_ad;
        }

        public String getDownload_category() {
            return download_category;
        }

        public void setDownload_category(String download_category) {
            this.download_category = download_category;
        }

        public String getDownload_domain() {
            return download_domain;
        }

        public void setDownload_domain(String download_domain) {
            this.download_domain = download_domain;
        }

        public String getWeb_template() {
            return web_template;
        }

        public void setWeb_template(String web_template) {
            this.web_template = web_template;
        }

        public String getCustom_market_url() {
            return custom_market_url;
        }

        public void setCustom_market_url(String custom_market_url) {
            this.custom_market_url = custom_market_url;
        }

        public String getAuthen_status() {
            return authen_status;
        }

        public void setAuthen_status(String authen_status) {
            this.authen_status = authen_status;
        }

        public boolean isHas_combo() {
            return has_combo;
        }

        public void setHas_combo(boolean has_combo) {
            this.has_combo = has_combo;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(int updated_at) {
            this.updated_at = updated_at;
        }

        public int getExpired_at() {
            return expired_at;
        }

        public void setExpired_at(int expired_at) {
            this.expired_at = expired_at;
        }

        public int getStale_at() {
            return stale_at;
        }

        public void setStale_at(int stale_at) {
            this.stale_at = stale_at;
        }

        public boolean isIs_owner() {
            return is_owner;
        }

        public void setIs_owner(boolean is_owner) {
            this.is_owner = is_owner;
        }

        public boolean isDownload_domain_https_ready() {
            return download_domain_https_ready;
        }

        public void setDownload_domain_https_ready(boolean download_domain_https_ready) {
            this.download_domain_https_ready = download_domain_https_ready;
        }

        public boolean isShow_refresh_stale_at() {
            return show_refresh_stale_at;
        }

        public void setShow_refresh_stale_at(boolean show_refresh_stale_at) {
            this.show_refresh_stale_at = show_refresh_stale_at;
        }

        public String getIcon_url() {
            return icon_url;
        }

        public void setIcon_url(String icon_url) {
            this.icon_url = icon_url;
        }

        public MasterReleaseBean getMaster_release() {
            return master_release;
        }

        public void setMaster_release(MasterReleaseBean master_release) {
            this.master_release = master_release;
        }

        public static class MasterReleaseBean implements Serializable{
            /**
             * version : 1.0.0
             * build : 100
             * release_type :
             * distribution_name :
             * supported_platform : null
             * created_at : 1690800310
             */

            private String version;
            private String build;
            private String release_type;
            private String distribution_name;
            private Object supported_platform;
            private int created_at;

            public String getVersion() {
                return version;
            }

            public void setVersion(String version) {
                this.version = version;
            }

            public String getBuild() {
                return build;
            }

            public void setBuild(String build) {
                this.build = build;
            }

            public String getRelease_type() {
                return release_type;
            }

            public void setRelease_type(String release_type) {
                this.release_type = release_type;
            }

            public String getDistribution_name() {
                return distribution_name;
            }

            public void setDistribution_name(String distribution_name) {
                this.distribution_name = distribution_name;
            }

            public Object getSupported_platform() {
                return supported_platform;
            }

            public void setSupported_platform(Object supported_platform) {
                this.supported_platform = supported_platform;
            }

            public int getCreated_at() {
                return created_at;
            }

            public void setCreated_at(int created_at) {
                this.created_at = created_at;
            }

            @Override
            public String toString() {
                return "{" +
                        "version='" + version + '\'' +
                        ", build='" + build + '\'' +
                        ", release_type='" + release_type + '\'' +
                        ", distribution_name='" + distribution_name + '\'' +
                        ", supported_platform=" + supported_platform +
                        ", created_at=" + created_at +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "{" +
                    "id='" + id + '\'' +
                    ", user_id='" + user_id + '\'' +
                    ", org_id='" + org_id + '\'' +
                    ", type='" + type + '\'' +
                    ", name='" + name + '\'' +
                    ", shortX='" + shortX + '\'' +
                    ", bundle_id='" + bundle_id + '\'' +
                    ", genre_id=" + genre_id +
                    ", is_opened=" + is_opened +
                    ", close_downloaded_ad=" + close_downloaded_ad +
                    ", download_category='" + download_category + '\'' +
                    ", download_domain='" + download_domain + '\'' +
                    ", web_template='" + web_template + '\'' +
                    ", custom_market_url='" + custom_market_url + '\'' +
                    ", authen_status='" + authen_status + '\'' +
                    ", has_combo=" + has_combo +
                    ", created_at=" + created_at +
                    ", updated_at=" + updated_at +
                    ", expired_at=" + expired_at +
                    ", stale_at=" + stale_at +
                    ", is_owner=" + is_owner +
                    ", download_domain_https_ready=" + download_domain_https_ready +
                    ", show_refresh_stale_at=" + show_refresh_stale_at +
                    ", icon_url='" + icon_url + '\'' +
                    ", master_release=" + master_release +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "{" +
                "apps_count=" + apps_count +
                ", page_size=" + page_size +
                ", items=" + items +
                '}';
    }
}
