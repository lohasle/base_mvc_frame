package com.lohasle.baseframe.s4m3.pojo;

import java.util.Map;



/**
 * @author fule
 * @version Revision 1.0.0
 * @see:
 * @创建日期：2013-10-23
 * @功能说明： 订单查询类 POJO
 */
public class OrderQryParamsBean {
	
	public static final String Y						= "Y";
	
	public static final String N						= "N";
	
	/**
	 * 未回款
	 */
	public static final String NOT_RECEIVED			= N;
	
	/**
	 * 已回款
	 */
	public static final String RECEIVED				= Y;
	
	/**
	 * 未逾期
	 */
	public static final String NOT_OVERDUE				= N;
	
	/**
	 * 已逾期
	 */
	public static final String OVERDUE					= Y;
	
	/**
	 * 未签约
	 */
	public static final String UN_SIGN					= N;
	
	/**
	 * 已签约
	 */
	public static final String SIGN					= Y;
	
	/**
	 * 未签合同 仅适用于与收款单
	 */
	public static final String UN_SIGN_CONTRACT		= N;
	
	/**
	 * 已签约合同 仅适用于与收款单
	 */
	public static final String SIGN_CONTRACT			= Y;
	

	/**
	 * creditType 贷款方式  mortgage按揭   
	 */
	public static final String CREDITTYPE_MORTGAGE = "mortgage" ;
	
	/**
	 *   fund公积金    
	 */
	public static final String CREDITTYPE_FUND = "fund" ;

	/**
	 *  combination组合贷
	 */
	public static final String CREDITTYPE_COMBINATION = "combination" ;

	
	
	/**
	 * 销售单
	 */
	public static final String ORDER_SALE				= "sale";
	
	/**
	 * 租赁单
	 */
	public static final String ORDER_LEASE				= "lease";

    /**
     * 回款单类型
     */
    public static final String ORDER_PAY			= "rec_pay_order";
	
	/**
	 * 按揭单
	 */
	public static final String ORDER_MORTGAGE			= "mortgage";
	
	/**
	 * 面积补差单
	 */
	public static final String ORDER_FOLLOW1			= "follow1";
	
	/**
	 * 交房单子
	 */
	public static final String ORDER_FOLLOW2			= "follow2";
	
	/**
	 * 办证单子
	 */
	public static final String ORDER_FOLLOW3			= "follow3";
	
	/**
	 * 楼栋key
	 */
	public static final String ROOM_KEY_BUILDING_NO	= "buildingNo";
	
	/**
	 * 房屋key
	 */
	public static final String ROOM_KEY_ROOM_NO		= "roomNo";
	
	/**
	 * 订单类型
	 * sale销售单
	 * lease租赁单
	 * mortgage按揭订单
	 * follow1面积补差单子
	 * follow2交房单子
	 * follow3办证单子
	 */
	private String orderType;
	
	/**
	 * 签约情况 根据情况可以为null
	 * N未签约
	 * Y已签约
	 */
	private String signState;
	
	/**
	 * 楼栋号或者房号（{“buildingNo”:”13”,”roomNo”:”203”}）
	 */
	private Map<String, String> roomKey;
	
	/**
	 * 客户姓名或者联系电话（支持模糊查询）
	 */
	private String custKey;
	
	/**
	 * 逾期标示
	 */
	private String overdueFlag;
	
	/**
	 * 回款情况
	 */
	private String receivedPay;
	
	/**
	 * 订单创建开始时间
	 */
	private String orderBeginTime;
	
	/**
	 * 订单创建结束时间
	 */
	private String orderEndTime;
	
	/**
	 * 拓展查询条件值
	 * 关于拓展查询条件
	 * extQryKey 备注
	 * orderLoansState 按揭流程状态        1. 资料待补充2. 资料审核 3. 待办他项权证4. 等待银行放款 5. 放款成功
	 * 
	 * orderAreaReserveState N 已补差
	 * Y 未补差
	 * 没有值就是所有 补差状态值
	 * 
	 * orderSubmittedState N 已交房
	 * Y 未交房
	 * 没有值就是所有 交房状态值
	 * 
	 * orderAccreditationState N 已办证
	 * Y 未办证
	 * 没有值就是所有 办证状态值
	 * 
	 * contractState 合同签约情况 Y已签合同 N未签合同 没有就是所有
	 * 
	 * creditType 贷款方式  mortgage按揭     fund公积金     combination组合贷
     *
     * isChangeStaffId  是否需要统计本组下的员工  Y 统计 N不统计
	 * 
	 */
	private Map extQryKeyMap;
	
	/**
	 * 销售代表ID 预留 2013-10-23
	 */
	private String staffId;
	
	/**
	 * @return staffId属性
	 */
	public String getStaffId() {
		return staffId;
	}
	
	/**
	 * @param staffId
	 *            设置staffId属性
	 */
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	
	/**
	 * @return orderType属性
	 */
	public String getOrderType() {
		return orderType;
	}
	
	/**
	 * @param orderType
	 *            设置orderType属性
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	/**
	 * @return signState属性
	 */
	public String getSignState() {
		return signState;
	}
	
	/**
	 * @param signState
	 *            设置signState属性
	 */
	public void setSignState(String signState) {
		this.signState = signState;
	}
	
	/**
	 * @return roomKey属性
	 */
	public Map<String, String> getRoomKey() {
		return roomKey;
	}
	
	/**
	 * @param roomKey
	 *            设置roomKey属性
	 */
	public void setRoomKey(Map<String, String> roomKey) {
		this.roomKey = roomKey;
	}
	
	/**
	 * @return custKey属性
	 */
	public String getCustKey() {
		return custKey;
	}
	
	/**
	 * @param custKey
	 *            设置custKey属性
	 */
	public void setCustKey(String custKey) {
		this.custKey = custKey;
	}
	
	/**
	 * @return overdueFlag属性
	 */
	public String getOverdueFlag() {
		return overdueFlag;
	}
	
	/**
	 * @param overdueFlag
	 *            设置overdueFlag属性
	 */
	public void setOverdueFlag(String overdueFlag) {
		this.overdueFlag = overdueFlag;
	}
	
	/**
	 * @return receivedPay属性
	 */
	public String getReceivedPay() {
		return receivedPay;
	}
	
	/**
	 * @param receivedPay
	 *            设置receivedPay属性
	 */
	public void setReceivedPay(String receivedPay) {
		this.receivedPay = receivedPay;
	}
	
	/**
	 * @return orderBeginTime属性
	 */
	public String getOrderBeginTime() {
		return orderBeginTime;
	}
	
	/**
	 * @param orderBeginTime
	 *            设置orderBeginTime属性
	 */
	public void setOrderBeginTime(String orderBeginTime) {
		this.orderBeginTime = orderBeginTime;
	}
	
	/**
	 * @return orderEndTime属性
	 */
	public String getOrderEndTime() {
		return orderEndTime;
	}
	
	/**
	 * @param orderEndTime
	 *            设置orderEndTime属性
	 */
	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	
	/**
	 * @return extQryKeyMap属性
	 */
	public Map getExtQryKeyMap() {
		return extQryKeyMap;
	}
	
	/**
	 * @param extQryKeyMap
	 *            设置extQryKeyMap属性
	 */
	public void setExtQryKeyMap(Map extQryKeyMap) {
		this.extQryKeyMap = extQryKeyMap;
	}



    public String toString() {
        return "OrderQryParamsBean{" +
                "orderType='" + orderType + '\'' +
                ", signState='" + signState + '\'' +
                ", roomKey=" + roomKey +
                ", custKey='" + custKey + '\'' +
                ", overdueFlag='" + overdueFlag + '\'' +
                ", receivedPay='" + receivedPay + '\'' +
                ", orderBeginTime='" + orderBeginTime + '\'' +
                ", orderEndTime='" + orderEndTime + '\'' +
                ", extQryKeyMap=" + extQryKeyMap +
                ", staffId='" + staffId + '\'' +
                '}';
    }
}
