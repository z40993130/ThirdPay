package com.yifu.platform.wxapi.yibao;

public class YiBaoConfig {
	/**
	 * 商户号
	 */
	public static final String MERCHANTACCOUNT = "10000418926";
	/**
	 * 服务器回调地址
	 */
	public static final String NOTIFY_URL = "http://123.57.237.83/wfybcborder";
	
	/**
	 * 商户的RSA私钥
	 */
	public static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALD0Tou2w7EHbP3q5wi5PG5xrvC0CBawXxSI1PlZAGo2iFYhaBK6SsB5UiYT64fSR3YemQGS2vSqQii5vYdOfrffvvDprrr7Vo7BziS6sJQ9B0/DzwN2zY7jJBCz55CLMBsZCtuqDNVxTcsOcZnrgSSMqnhk+usuR4hPoV9qABeHAgMBAAECgYAfnth2UOdxN/F7AkHcpjUtSzVGn/UeENA8vCLKl+PiFvKP6ZJOXmnDMSrD0SVydNn+OoN+634i4FXIL0C18Anmh4IlQM9hj+rFTg1bMSUHvSPKoZpoEfjR0R+3TQF8PycBbaIWgLV/5NA8dMld0DvF5d8bbqpgH6FzEXZPvF8OgQJBANwHRhCu+o/JoCoH0coVhNFuobVYZU0pQRlfDaE4ph0+daiJ4HlT630JrBFb728Ga7E81dsfGMSi1N6QSipJMEECQQDN4kb+O/ecDNQrEsjA0LqDXkaKsRP6iU/HVNyr4Z/7ojHws0F5Vypj1euCII+V6U7StMKRbSaB1GI8Bs34llXHAkEAnIc0KiRBLk+S+LOtZGVgoplgwyEKmBUUMdd0W9BwJHfNvkOwBMBV1BMwbP0JXeOkc2dDAGqj9Sed5mOhz2lXwQJAVeA0TIcm2Ohg9zZ2ljZ6FaGVOvRxqObtZ+91vBv4ZzVYL1YV0U8SV2I7QaPjQFx4jFrpbU9h6HV2JCOSdkX+sQJBAJ+PfNA0b25HuY9n4cTk/hLc2TCWVDsPnONuhNpuRpXqxu9L0p2aHX5JLf1kTUoYxqmlEjx6IYcObcB9Snw0Tf0=";
	/**
	 * 商户RSA公钥(代码中用不到)
	 */
	public static final String RSA_PUBLIC_KEY = "";
	/**
	 * 易宝的RSA公钥
	 */
	public static final String YIBAO_RSA_PUBLIC_KEY="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCKcSa7wS6OMUL4oTzJLCBsE5KTkPz9OTSiOU6356BsR6gzQ9kf/xa+Wi1ZANTeNuTYFyhlCI7ZCLW7QNzwAYSFStKzP3UlUzsfrV7zge8gTgJSwC/avsZPCWMDrniC3HiZ70l1mMBK5pL0H6NbBFJ6XgDIw160aO9AxFZa5pfCcwIDAQAB";
	/**
	 * 请求网关
	 */
	public static final String REQUEST_URL = "https://ok.yeepay.com/paymobile/api/pay/request";
}
