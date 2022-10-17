

async function createUser(){   

    let datos = {};    ;
    datos.name = document.getElementById('name').value;
    datos.lastName = document.getElementById('last_name').value;
    datos.email = document.getElementById('email').value ;
    datos.password = document.getElementById('password').value ;
    datos.image = document.getElementById('image').value;

    console.log(datos.password)
   
   let repeat_password = document.getElementById('repet_password').value;
   console.log(repeat_password)

   if(repeat_password != datos.password){
    alert('Passwords are different');
    return;
    };
   
    const request = await fetch('auth/register', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },

      body: JSON.stringify(datos)    
    });

    if(request.ok){
      alert("Registered user successfully")
      window.location.href = 'index.html';
    } else{
      alert("Datos incorrectos")
    }

    
}