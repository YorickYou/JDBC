<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<p style="text-align: center;">
第${pb.cp }页/共${pb.totalPage }页　
<c:choose>
	<c:when test="${pb.cp eq 1 }">首页</c:when>
	<c:otherwise><a href="${pb.url}&cp=1">首页</a></c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${pb.cp eq 1 }">上一页</c:when>
	<c:otherwise><a href="${pb.url}&cp=${pb.cp-1 }">上一页</a>　</c:otherwise>
</c:choose>
<!--处理页码下拉菜单  -->
<select name="cp">
<c:forEach begin="1" end="${pb.totalPage }" var="i">
	<option value="${i }" <c:if test="${i eq pb.cp }">selected="selected" </c:if>>
	${i }
	</option>
</c:forEach>
</select>



<%-----页面列表 --%>
<%--
    > 总页数<=10：begin=1, end=tp
    > begin=cp-4, end=cp+5
    > 头溢出：if(begin < 1) begin=1, end=10
    > 尾溢出：if(end > tp) end=tp, begin=tp-9
 --%>
<%-----页面列表 --%>

<c:choose>
	<c:when test="${pb.totalPage <= 10 }"><%--总页数<=10：begin=1, end=tp --%>
		<c:set var="begin" value="1"/>
		<c:set var="end" value="${pb.totalPage }"/>
	</c:when>
	<c:otherwise>
		<%-- begin=cp-4, end=cp+5 --%>
		<c:set var="begin" value="${pb.cp-4 }"/>
		<c:set var="end" value="${pb.cp+5 }"/>
		<c:if test="${begin < 1 }"><%--头溢出：if(begin < 1) begin=1, end=10 --%>
			<c:set var="begin" value="1"/>
			<c:set var="end" value="10"/>
		</c:if>
		<c:if test="${end > pb.totalPage }"><%-- 尾溢出：if(end > tp) end=tp, begin=tp-9 --%>
			<c:set var="begin" value="${pb.totalPage-9 }"/>
			<c:set var="end" value="${pb.totalPage }"/>
		</c:if>	
	</c:otherwise>
</c:choose>

<c:forEach var="i" begin="${begin }" end="${end }">
  <c:choose>
  	<c:when test="${i eq pb.cp }">[${i}]</c:when>
  	<c:otherwise>
  		<a href="${pb.url}&cp=${i}">[${i}]</a>
  	</c:otherwise>
  </c:choose>
</c:forEach>



<c:choose>
	<c:when test="${pb.cp eq pb.totalPage }">下一页</c:when>
	<c:otherwise><a href="${pb.url}&cp=${pb.cp+1 }">下一页</a>　</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${pb.cp eq pb.totalPage }">尾页</c:when>
	<c:otherwise><a href="${pb.url}&cp=${pb.totalPage}">尾页</a>　</c:otherwise>
</c:choose>　

<!--处理页码下拉菜单  -->
<select name="cp">
<c:forEach begin="1" end="${pb.totalPage }" var="i">
	<option value="${i }" <c:if test="${i eq pb.cp }">selected="selected" </c:if>>
	${i }
	</option>
</c:forEach>
</select>
</p>
