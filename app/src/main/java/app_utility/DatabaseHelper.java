package app_utility;

public class DatabaseHelper {

    private int _id;
    private String _main_category_name;
    private String _main_category_description;
    private String _sub_category_first_names;
    private String _sub_category_first_name;
    private String _sub_category_second_name;
    private String _sub_category_third_name;
    private String _product_name;
    private String _product_image_path;
    private String _product_tech_specs;
    private String _product_description;

    public DatabaseHelper() {

    }

    public int get_id() {
        return this._id;
    }

    // setting id
    public void set_id(int id) {
        this._id = id;
    }

    public String get_main_category_name() {
        return this._main_category_name;
    }

    public void set_main_category_name(String main_category_name) {
        this._main_category_name = main_category_name;
    }

    public String get_main_category_description() {
        return this._main_category_description;
    }

    public void set_main_category_description(String main_category_description) {
        this._main_category_description = main_category_description;
    }

    public String get_sub_category_first_names() {
        return this._sub_category_first_names;
    }

    public void set_sub_category_first_names(String sub_category_first_names) {
        this._sub_category_first_names = sub_category_first_names;
    }

    public String get_sub_category_first_name() {
        return this._sub_category_first_name;
    }

    public void set_sub_category_first_name(String sub_category_first_name) {
        this._sub_category_first_name = sub_category_first_name;
    }

    public String get_sub_category_second_name() {
        return this._sub_category_second_name;
    }

    public void set_sub_category_second_name(String sub_category_second_name) {
        this._sub_category_second_name = sub_category_second_name;
    }

    public String get_sub_category_third_name() {
        return this._sub_category_third_name;
    }

    public void set_sub_category_third_name(String sub_category_third_name) {
        this._sub_category_third_name = sub_category_third_name;
    }

    public String get_product_name() {
        return this._product_name;
    }

    public void set_product_name(String product_name) {
        this._product_name = product_name;
    }

    public String get_product_image_path() {
        return this._product_image_path;
    }

    public void set_product_image_path(String product_image_path) {
        this._product_image_path = product_image_path;
    }

    public String get_product_tech_specs() {
        return this._product_tech_specs;
    }

    public void set_product_tech_specs(String product_tech_specs) {
        this._product_tech_specs = product_tech_specs;
    }

    public String get_product_description() {
        return this._product_description;
    }

    public void set_product_description(String product_description) {
        this._product_description = product_description;
    }
}
