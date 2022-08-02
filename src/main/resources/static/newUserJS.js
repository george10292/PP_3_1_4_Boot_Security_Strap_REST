const url1 = 'http://localhost:8080/api/admin/users/save'
const form = document.getElementById("newUserForm")
form.addEventListener('submit', async (e) => {
    e.preventDefault();
    let name = document.querySelector('#name');
    let age = document.querySelector('#age');
    let email = document.querySelector('#email')
    let password = document.querySelector('#password');
    let surname = document.querySelector('#surname');
    let roles =$('#roles').val();
    let role;
    if (roles[0] === "1" && roles.length == 1) {
        role = [{
            id: 1,
            name: "ADMIN",
            authority: "ADMIN"
        }]
    }
    if (roles[0] === "2" && roles.length == 1) {
        role = [{
            id: 2,
            name: "USER",
            authority: "USER"
        }]
    }if (roles.length == 2) {
        role=[{
            id: 1,
            name: "ADMIN",
            authority: "ADMIN"
        },
            {
                id: 2,
                name: "USER",
                authority: "USER"
            }
        ]
    }

    let jsonobject =
        {
            name: name.value,
            age: age.value,
            email: email.value,
            password: password.value,
            surname: surname.value,
            roles: role,
        }
    try {
        const responce = await fetch(url1, {
            method: 'POST',
            body: JSON.stringify(jsonobject),
            headers: {
                'Content-Type': 'application/json',
            }
        });
        const json = await responce.json()
        $('#myTab li:first-child a').tab('show');
        renderPage();

    } catch (e) {
        console.error(e)
        alert('there was an error')
    }

})