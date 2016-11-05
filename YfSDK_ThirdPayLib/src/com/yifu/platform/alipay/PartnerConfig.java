package com.yifu.platform.alipay;

public class PartnerConfig {
	// 合作商户ID。用签约支付宝账号登录b.alipay.com后，在账户信息页面获取。
	public static final String PARTNER = "2088221950553378";

	// 商户收款的支付宝账号
	public static final String SELLER  = "2638815014@qq.com";
	// 商户（RSA）私钥(能用)
	public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALqPrhuZG58dkWWbPh4Obv3pHtf4LbF3Eqqhd1yNaeojGH0QOoejkSdRPSYB6xd4s2UA+PhvgmLwmgT2N4jNTU81heY8INwDUHM9qTt98DbkqI423VnZxVGKc6G0mRNSMmaeHsw/V8YC1FwYyZRa4KmJAcDFfb2+eby/JmM7Z4mNAgMBAAECgYEAoqb2ExWv4U/0HfP1elr3JeSDgaPcoqa/3Ygc+tJID1w903u8q9lNikvn1iVCBBq1vU/VwTPnJ7/BaKYJ0Pm9dd5fLfROk3j818L+ivtsSK6NKvjIWPqmBk+W+6pyD2BPTtNNtmObkvDfeTGabhW+X0LcuMSv5LOUO8le4BbpefkCQQDhkbAwhWE3aO0WWI+b51PKy3yoIE7qGrtSgGTYkXkkE6n873Y/jWBoAt4WtUBCfpisRrKol2lGs1gaEasD9BMDAkEA07rOmHxufs35OnjMPYuY5vVIXur8URVQanuo4k/6qLWM4kqYkBFokZrUzmLvBqpkg63AA3dTp6FVkxpJYcQELwJBAMvbnA0t7f9iz6p7XUZ8GSlVIBLeKBPBFvxn1zw2tPHa560VSZwEDFXUCZ0iL7IosZg4yKw/MsDXws7EmvcWtbsCQGnChi/k98yfVj6+2EZl1JqJKv0+o4pc+y41Vsa07KAZD6Z5XHuaNoGEtYfiI0NRGaQsxhz1HfQ9wmEYYE9VekECQAr8ehh2W5HIRp35IjFsRKO8iHMe0uhIex3Il4u0qEZ+eaPlVkTKK2Dg+x6S1i5kBRwMabCbnlUKpMob27E+/XI=";
	//不能用
	//public static final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMtQcg/rlsW5a8bEuceIJ2xEJ5Ft2cUerhKcNpqxBtuqpmPDzTe/hdzC0bwe+SNnqwJ6gINXvXD8+eNb7CEwgFH0H6X6eYsY9PBi2ntsNxc6dMbqLuD/gJYudiyRFxlkEb6oHZ6pn15TVpX3RxzNITpaOYW50Hh3lniXAeWXpSt5AgMBAAECgYBWnAha+9MYm6pr3DX/5+JTXp00eIVCUZV53A3uy+bOAN4staZgTzgpGNzbtJ4RFQJmZeUFDoVm8n1I+0mVweGHRQZfwhVOy+u34SUh0kuNCmtN9izmXjrJILO4OxseOjGZtQG2O8J4gNzRQhr1EjelxqnXxscdAeVxOB5bnTc/AQJBAPUQMrB9Zj+ph4sjsb5zZQN17i7ahnSrv7CQq35XTbinuLHUCKIM1TKSN814f8TX4FHWvOZo3JUxRBNFU4OQ9KkCQQDUY0XNlDAidtIfED0YEMO1/ioge1tNXYu6BhEDBRs7eM5HK2y3OI9emHGc6/IZIkU7pecZNvmI1v2zvBijgPJRAkEA6n4DYum7CzO25SbUj35vq+4OaXrkqYMmO1dTIeN8FTM4AcD4Oce5wVg0WUHyUHKYzPaDbp3sZD3t+9qcXQo5cQJADUEsyAmpf10e++VNisTxEdiM1H4eUpO19qQOR7v32RxO804YeM7E08h7vqlS8JKc834FECPIKuwP5Tml5/5SIQJBAL9dF4FejeAw/CqnqTIDJR5NS+oB84PC60MTDvdONBKg7U1DriiPaHRazQFhf8vlB49kkaaDp0tNLHY0B4dqItY=";
	// 支付宝（RSA）公钥 用签约支付宝账号登录b.alipay.com后，在密钥管理页面获取。
	public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	//public static final String RSA_ALIPAY_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC3sIyr2B87x3BNZvjj5mNFd7EJGwgRYH+CyKMENXUIhiYF7G6iw54hDOYc1j1d6cMk+Vme7oQyYW8dQM4kb633CCvzYcY1QRiK04RAmPAHaO2tiQKUoXXx7dBSJuHoiqxYFD8otz7dyRIR4iw/gkETVnzVhKUhcmsM/Jm0w6CyiwIDAQAB";
	// 支付宝安全支付服务apk的名称，必须与assets目录下的apk名称一致
	public static final String ALIPAY_PLUGIN_NAME = "alipay.apk";
	// 通知商户服务器的回调地址
	public static final String SERVER_NOTIFY_URL = "http://123.57.237.83/alipaycborder";
}
