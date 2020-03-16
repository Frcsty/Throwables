package me.frosty.throwables.util;

import com.codeitforyou.lib.api.general.StringUtil;

class DataParser
{

    private static final String DATA_DELIMITER = ";";
    private static final String PARAM_SPLITTER = ",";

    private final String[] params;
    private final String   text;
    private final String   extensiveModifier;

    DataParser(final String data)
    {
        final String[] parts = data.split(DATA_DELIMITER);

        this.params = parts[0].split(PARAM_SPLITTER);
        this.text = parts[1];
        this.extensiveModifier = parts[2];
    }

    String getType()
    {
        return params[0].toUpperCase();
    }

    int getTimer()
    {
        return Integer.parseInt(params[1]);
    }

    int getModifier()
    {
        return Integer.parseInt(params[2]);
    }

    String getText()
    {
        return StringUtil.translate(text);
    }

    String getExtensiveModifier()
    {
        return extensiveModifier.toUpperCase();
    }

}
