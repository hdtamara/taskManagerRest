async function login(){
    let datos = {};    ;
    datos.email = document.getElementById('email').value;
    datos.password = document.getElementById('password').value; 
    
    
    const request = await fetch('auth/login', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },

      body: JSON.stringify(datos)
    }).then(request=>request.clone().json());
   
    let roles = []
    request.authorities.forEach(element => {
      roles.push(element.authority)
    });

    if(request != ""){

        localStorage.token =request.beares+request.token;
        localStorage.email = request.email;
        localStorage.rol = roles;
        
        if(roles.includes('ROL_ADMIN')){
          window.location.href = 'menu.html'
        } else{
          window.location.href = 'taskslist.html'
        }
        
    } else{
        alert("Las credenciales son incorrectas. Intente nuevamente")
    }
     
  
  }