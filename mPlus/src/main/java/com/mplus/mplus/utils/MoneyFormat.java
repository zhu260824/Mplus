package com.mplus.mplus.utils;

public class MoneyFormat {
	private static final char[] UNIT = { '亿', '拾', '佰', '仟', '万', '拾', '佰', '仟' };
	private static final char[] CHAINIESFIGURE2 = { '零', '壹', '贰', '叁', '肆',
			'伍', '陆', '柒', '捌', '玖' };

	// 整数部分的转换
	public static String toChineseCharI(String intString)
			throws NumberFormatException {

		// 用来存放转换后的大写数字,因为是StringBuffer类型,所以顺便把没有转换
		// 的数字倒序排列一下,省一个变量.
		StringBuffer ChineseCharI = new StringBuffer(intString);
		// 倒序的数字,便于同单位合并
		String rintString = ChineseCharI.reverse().toString();
		// 清空一下
		ChineseCharI.delete(0, ChineseCharI.length());
		// 单位索引
		int unitIndex = 0;
		// 数字长度
		int intStringLen = intString.length();
		// 一位由字符转换的数字
		int intStringnumber = 0;
		// 补0
		boolean addZero = false;
		boolean flagZero = false;
		for (int i = 0; i < intStringLen; i++) {
			// 按单位长度循环索引
			unitIndex = i % UNIT.length;

			// 异常检查
			if (!Character.isDigit(rintString.charAt(i))) {
				throw new NumberFormatException("数字中含有非法字符");
			}
			intStringnumber = Character.digit(rintString.charAt(i), 10);

			// 如果当前位是0,开启补0继续循环直到不是0的位
			if (intStringnumber == 0) {
				addZero = true;
				if (i != 0 && i % 4 == 0) {
					if (addZero && ChineseCharI.length() != 0) {
						ChineseCharI.append(CHAINIESFIGURE2[0]);
						addZero = false;
					}
					flagZero = true;
					ChineseCharI.append(UNIT[unitIndex]);
				}
			} else {
				// 若当前位不是第一位且补0开启
				if (addZero && ChineseCharI.length() != 0 && !flagZero) {
					ChineseCharI.append(CHAINIESFIGURE2[0]);
				}
				flagZero = false;
				// 插入单位
				// 个位数后面不需 要单位
				if (i > 0) {
					System.out.println(i);
					ChineseCharI.append(UNIT[unitIndex]);
				}

				// 插入大写数字
				ChineseCharI.append(CHAINIESFIGURE2[intStringnumber]);
				// 补0关闭
				addZero = false;
			}
		}

		// 异常处理
		if (ChineseCharI.length() == 0) {
			return "零";
		}
		return ChineseCharI.reverse().toString() + "圆整";

	}
}
