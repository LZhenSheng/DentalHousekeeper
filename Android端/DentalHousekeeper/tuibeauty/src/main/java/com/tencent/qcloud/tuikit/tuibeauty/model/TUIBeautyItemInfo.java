package com.tencent.qcloud.tuikit.tuibeauty.model;

import android.text.TextUtils;

import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.xmagic.XmagicProperty;

import static com.tencent.qcloud.tuikit.tuibeauty.model.utils.FileUtils.getResPath;

/**
 * 美颜面板 item 相关属性
 * 成员变量名和 assets/default_beauty_data.json 的 key 相对应，便于 json 解析
 */
public class TUIBeautyItemInfo {

    private long item_id;               // long, item id，item 唯一标识

    private int item_type;              // int, item 类型，item 的功能
    private int item_level;             // int, 特效级别，-1代表无特效级别，即不显示SeekBar
    private int item_display_min_value;
    private int item_display_max_value;
    private int item_inner_min_value;
    private int item_display_default_value;
    private int item_inner_max_value;

    private String item_name;           // string, item 名称
    private String item_material_url;   // string, 素材 url
    private String item_material_path;  // string, 素材本地路径
    private String item_icon_normal;    // drawable, item 常规 icon
    private String item_icon_select;    // drawable, item 选中 icon
    private String item_key;
    private String item_xmagic_id;
    private String item_res_name;
    private int    item_category;

    private XmagicProperty<XmagicProperty.XmagicPropertyValues> property;


    public void setProperty(XmagicProperty<XmagicProperty.XmagicPropertyValues> property) {
        this.property = property;
    }


    public long getItemId() {
        return item_id;
    }

    public int getItemType() {
        return item_type;
    }

    public void setItemIcNormal(String itemIconNormal) {
        this.item_icon_normal = itemIconNormal;
    }

    public String getItemName() {
        return item_name;
    }

    public void setItemName(String itemName) {
        this.item_name = itemName;
    }

    public String getItemIconNormal() {
        return item_icon_normal;
    }

    public String getItemIconSelect() {
        return item_icon_select;
    }

    public void setItemLevel(int item_level) {
        this.item_level = item_level;
    }

    public int getItemLevel() {
        return item_level;
    }

    public String getItemMaterialUrl() {
        return item_material_url;
    }

    public String getItemMaterialPath() {
        return item_material_path;
    }

    public void setItemMaterialPath(String item_material_path) {
        this.item_material_path = item_material_path;
    }

    public void setItemCategory(int item_category) {
        this.item_category = item_category;
    }

    public XmagicProperty<XmagicProperty.XmagicPropertyValues> getProperty() {
        if (property == null || property.category == null) {
            createProperty();
        }
        return property;
    }

    public void createProperty() {
        XmagicProperty.Category category = null;
        String effDirs = "/images/beauty/";
        String resPath = null;
        switch (item_category) {
            case TUIBeautyConstants.TAB_TYPE_BEAUTY:
                category = XmagicProperty.Category.BEAUTY;
                resPath = TextUtils.isEmpty(item_res_name) ? null : effDirs + item_res_name;
                break;
            case TUIBeautyConstants.TAB_TYPE_LUT:
                category = XmagicProperty.Category.LUT;
                break;
            case TUIBeautyConstants.TAB_TYPE_MOTION:
            case TUIBeautyConstants.TAB_TYPE_SEGMENTATION:
                category = XmagicProperty.Category.MOTION;
                resPath = TextUtils.isEmpty(item_res_name) ? null : getResPath() + item_res_name;
                break;
            case TUIBeautyConstants.TAB_TYPE_MAKEUP:
                category = XmagicProperty.Category.MAKEUP;
                resPath = TextUtils.isEmpty(item_res_name) ? null : getResPath() + item_res_name;
                break;
            default:
                TXCLog.e("TUIBeautyItemInfo", "switch category failed!,item category:" + item_category);
                break;
        }
        XmagicProperty.XmagicPropertyValues values = new XmagicProperty.XmagicPropertyValues(item_display_min_value, item_display_max_value, item_display_default_value, item_inner_min_value, item_inner_max_value);

        property = new XmagicProperty<>(category, item_xmagic_id, resPath, item_key, values);
    }

    @Override
    public String toString() {
        return "TUIBeautyItemInfo{" +
                "item_id=" + item_id +
                ", item_type=" + item_type +
                ", item_level=" + item_level +
                ", item_display_min_value=" + item_display_min_value +
                ", item_display_max_value=" + item_display_max_value +
                ", item_inner_min_value=" + item_inner_min_value +
                ", item_display_default_value=" + item_display_default_value +
                ", item_inner_max_value=" + item_inner_max_value +
                ", item_name='" + item_name + '\'' +
                ", item_material_url='" + item_material_url + '\'' +
                ", item_material_path='" + item_material_path + '\'' +
                ", item_icon_normal='" + item_icon_normal + '\'' +
                ", item_icon_select='" + item_icon_select + '\'' +
                ", item_key='" + item_key + '\'' +
                ", item_xmagic_id='" + item_xmagic_id + '\'' +
                ", item_res_name='" + item_res_name + '\'' +
                ", item_category=" + item_category +
                ", property=" + property +
                '}';
    }
}
