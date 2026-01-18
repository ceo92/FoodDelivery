<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>회원 등록</title>
</head>
<body>

<h2>회원 등록</h2>

<form action="/members/new" method="post">

    <div>
        이름: <input type="text" name="name" value="${member.name}">
    </div>

    <div>
        도시: <input type="text" name="city" value="${member.address.city}">
    </div>

    <div>
        거리: <input type="text" name="street" value="${member.address.street}">
    </div>

    <div>
        우편번호: <input type="text" name="zipcode" value="${member.address.zipcode}">
    </div>

    <div>
        <button type="submit">저장</button>
    </div>

</form>

</body>
</html>