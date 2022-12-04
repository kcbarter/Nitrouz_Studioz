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