window.onload = (event) => {
    let elements = document.getElementsByClassName("labels");

    for(var i = 0; i < elements.length; i++){
        elements[i].readOnly = true;
    }
};

document.getElementById("edit").onclick = function (){
    console.log("click");
    var button = document.getElementById("edit");
    let elements = document.getElementsByClassName("labels");
    if(button.innerText === "Edit"){
        for(var i = 0; i < elements.length; i++){
            elements[i].readOnly = false;
        }

        button.innerHTML = "Save";
    }

    else{
        let profileName = document.getElementById("profileName").value;
        let name = document.getElementById("name").value;
        let email = document.getElementById("email").value;
        let about = document.getElementById("about").value;
        let phone = document.getElementById("phone").value;


        fetch("/api/updateProfileInfo/")
            .then(res => {
                if(res.ok){
                    console.log("Response, " + res);
                    for(var i = 0; i < elements.length; i++){
                        elements[i].readOnly = true;
                    }

                    alert("Profile Saved");

                    button.innerHTML = "Edit";
                }
            })
            .catch(err => console.log("Error, " + err))
    }
};

//when hovering over profile_pic have a light gray overlay
document.getElementById("profile_pic_container").onmouseover = function () {
    // when hovering over profile_pic img change the image's overlay color
    document.getElementById("profile_pic").onmouseover = function () {
        document.getElementById("profile_pic").style.backgroundColor = "rgba(0, 0, 0, 0.5)";
    }

    // when not hovering over profile_pic img reset the image's background color
    document.getElementById("profile_pic").onmouseout = function () {
        document.getElementById("profile_pic").style.backgroundColor = "transparent";
    }
}

document.getElementById("profile_pic_container").onmouseover = function () {
    // when hovering over the profile_pic_overlay, change the image's background color
    document.getElementById("profile_pic_input").onmouseover = function () {
        document.getElementById("profile_pic").style.backgroundColor = "rgba(0, 0, 0, 0.5)";
    };

    // when not hovering over the profile_pic_overlay, reset the image's background color
    document.getElementById("profile_pic_input").onmouseout = function () {
        document.getElementById("profile_pic").style.backgroundColor = "transparent";
    };
};
