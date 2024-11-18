$(document).ready(function() {
    // Hàm đăng xuất
    $('#logout').click(function() {
        localStorage.clear();
        window.location.href = '/login';
    });

    // Hàm đăng nhập
    $('#Login').click(function() {
        var email = document.getElementById('email').value;
        var password = document.getElementById('password').value;
        var basicInfo = JSON.stringify({
            email: email,
            password: password
        });

        $.ajax({
            type: "POST",
            url: "/auth/login",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            data: basicInfo,
            success: function(data) {
                localStorage.token = data.token; // Lưu token vào localStorage
                window.location.href = "/user/profile";
            },
            error: function() {
                alert("Login failed!");
            }
        });
    });
});
