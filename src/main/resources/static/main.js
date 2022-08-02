async function renderPage() {
    fetch('http://localhost:8080/api/admin/users').then(
        res => {
            res.json().then(
                data => {
                    if (data.length > 0) {
                        var temp = "";
                        data.forEach((u, index) => {
                            temp += "<tr>";
                            temp += "<td>" + u.id + "</td>";
                            temp += "<td>" + u.name + "</td>";
                            temp += "<td>" + u.age + "</td>";
                            temp += "<td>" + u.surname + "</td>";
                            temp += "<td>" + u.email + "</td>";
                            temp += "<td>" + u.roles.map(function (item) {
                                return item['name'] + ' ';
                            }).join("") + "</td>"
                            temp += "<td> <a  data-href='http://localhost:8080/api/admin/users/" + u.id + "' style='color:white' class=\"btn btn-info eBtn\" data-index=" + index + ">Edit</a>"
                                + "</td>"
                            temp += "<td> <a data-href='http://localhost:8080/api/admin/users/" + u.id + "' class=\"btn btn-danger dBtn\" style='color:white' data-index=" + index + ">Delete</a>"
                                + "</td></tr>";

                        })
                        console.log('314')
                        renderEditModal();
                        renderDeleteModal();


                        document.getElementById("data").innerHTML = temp;
                    }
                }
            )

        }
    )
}

renderPage();

async function renderEditModal() {
    $(document).ready(function () {
        $('.table .eBtn').on('click', function (event) {
            event.preventDefault();
            let href = event.target.getAttribute('data-href')
            var text = $(this).text();
            $.get(href, function (user, status) {
                $('.myForm #id1').val(user.id);
                $('.myForm #name1').val(user.name);
                $('.myForm #age1').val(user.age);
                $('.myForm #surname1').val(user.surname);
                $('.myForm #email1').val(user.email);
                $('.myForm #password1').val(user.password);
                $('.myForm #roles1').val(user.roles);
            });


            $('.myForm #exampleModal').modal();

        })
    });
}

let formEdit = document.querySelector('.editUser')
formEdit.addEventListener('submit', async (e) => {
    e.preventDefault();
    let id = document.querySelector('#id1');
    let name = document.querySelector('#name1');
    let age = document.querySelector('#age1');
    let email = document.querySelector('#email1')
    let password = document.querySelector('#password1');
    let surname = document.querySelector('#surname1');
    let roles = $('#roles1').val();
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
    }
    if (roles.length == 2) {
        role = [{
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
            id: +id.value,
            name: name.value,
            age: +age.value,
            email: email.value,
            password: password.value,
            surname: surname.value,
            roles: role,
        }
    try {
        const responce = await fetch(`http://localhost:8080/api/admin/users`, {
            method: 'PUT',
            body: JSON.stringify(jsonobject),
            headers: {
                'Content-Type': 'application/json',
            }
        });
        const json = await responce.json()
        renderPage();
        $('#exampleModal').modal('hide');

    } catch (e) {
        console.error(e)
        alert('there was an error')
    }
})

async function renderDeleteModal(){
    $(document).ready(function () {
        $('.table .dBtn').on('click', function (event) {
            event.preventDefault();
            let href = event.target.getAttribute('data-href')
            $.get(href, function (user, status) {
                $('.myForm #id2').val(user.id);
                $('.myForm #name2').val(user.name);
                $('.myForm #age2').val(user.age);
                $('.myForm #surname2').val(user.surname);
                $('.myForm #email2').val(user.email);
                $('.myForm #password2').val(user.password);
                $('.myForm #roles2').val(user.roles);
            });


            $('.myForm #exampleModalDelete').modal();

        })
    })
}

let formDelete = document.querySelector('.deleteUser')
formDelete.addEventListener('submit', async (e) => {
    e.preventDefault();
    let id = document.querySelector('#id2');

    try {
        const responce = await fetch('/api/admin/users/'+id.value, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            }
        }).then(res => res.text())
            .then(res => console.log(res))

        renderPage();
        $('#exampleModalDelete').modal('hide');

    } catch (e) {
        console.log("Something went wrong")
    }
})