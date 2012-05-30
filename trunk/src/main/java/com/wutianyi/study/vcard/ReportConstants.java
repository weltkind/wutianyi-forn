package com.wutianyi.study.vcard;

public interface ReportConstants
{
    /**
     * 公用的
     */
    String display_name = "display_name";

    /**
     * report 相关的
     */
    String report_id = "id";
    String report_name = "name";

    /**
     * 头部相关
     */
    String header_vcard = "vcard";
    String header_delimiter = "delimiter";
    String header_extend = "extend";
    String header_multi = "multi";
    String header_auto_increment = "auto_increment";
    String header_parameter = "parameter";
    String header_count = "count";
    String header_split_length = "split_length";
    String header_escape_parameter = "escape_parameter";
    /**
     * 是否依据数据来决定是否显示 true：不依据 false：依据
     */
    String header_show = "show";

    /**
     * 值相关
     */
    String value_index = "index";
    String value_escape = "escape";
}
