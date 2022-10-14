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
    });
    console.log(request);
    const token = await request.text(); 

    if(request.ok){

        localStorage.token = token;
        localStorage.email = datos.email; 
        window.location.href = 'taskslist.html'
    } else{
        alert("Las credenciales son incorrectas. Intente nuevamente")
    }
     
  
  }