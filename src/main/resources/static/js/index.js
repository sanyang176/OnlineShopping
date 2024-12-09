function SetLogin(){
    const obj = document.getElementById("login");
    let loginStatus = obj.text;
    const userName = document.getElementById("userName")
    if(loginStatus === "登录"){
        obj.setAttribute("href","/login")
    }
    else{
        obj.setAttribute("href","javascript:void(0)")
        obj.text = "登录"
        userName.text=""
    }
}