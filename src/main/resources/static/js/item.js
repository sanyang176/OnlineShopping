function SetDisabled(element) {
    const obj = document.getElementById("link" + element.dataset.id);
    if(element.dataset.stocksize === "0"){
        obj.setAttribute("href","javascript:void(0)");
        obj.style.color='#ccc';
    }
    else{
        obj.setAttribute("href","/spike/" + element.dataset.id);
        obj.style.color='';
    }
}
