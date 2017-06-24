package com.asd.android.util;

import android.widget.EditText;

/**
 * Created by Administrator on 2016/5/5.
 */
public class EditTextUtil {
    private static EditTextUtil textViewUtil;

    public static EditTextUtil getInstance() {
        if (null == textViewUtil) {
            textViewUtil = new EditTextUtil();
        }
        return textViewUtil;
    }

    /**
     * 设置TextView显示的小数个数
     *
     * @param editText
     * @param size       小数个数
     * @return true:当前为有效位数字  false：反之
     */
    public boolean initTextViewNumberDecimalSizeAndCheck(EditText editText, int size) {
        String string = editText.getText().toString().trim();
        if (!StringUtil.getInstance().isEmpty(string)) {
            if (string.substring(0, 1).equals(".")) {
                editText.setText("");
                editText.setText("0.");
                editText.setSelection(editText.getText().length());
                return false;
            }
            if (string.contains(".") && string.length() > size + 1) {
                String lString = string.split("\\.")[0];
                try {
                    String rString = string.split("\\.")[1];
                    if (rString.length() > size) {
                        editText.setText("");
                        editText.setText(lString + "." + rString.substring(0, size));
                        editText.setSelection(editText.getText().length());
                    }
                    return true;
                } catch (Exception e) {
                    return true;
                }
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * 设置TextView显示的小数个数
     *
     * @param editText
     * @param size     小数个数
     */
    public void initTextViewNumberDecimalSize(EditText editText, int size) {
        String string = editText.getText().toString().trim();
        if (!StringUtil.getInstance().isEmpty(string)) {
            if (string.substring(0, 1).equals(".")) {
                editText.setText("");
                editText.setText("0.");
                editText.setSelection(editText.getText().length());
            }
            if (string.contains(".") && string.length() > size + 1) {
                String lString = string.split("\\.")[0];
                try {
                    String rString = string.split("\\.")[1];
                    if (rString.length() > size) {
                        editText.setText("");
                        editText.setText(lString + "." + rString.substring(0, size));
                        editText.setSelection(editText.getText().length());
                    }
                } catch (Exception e) {
                }
            }
        }
    }
}
