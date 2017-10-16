<?php
// start a session
// include connection
session_start();
include '../config/connection.php';
// select all from the asx_data table in database
// $sql = "select * from player";

// $records = mysqli_query($conn, $sql);

?>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<?php include("../includes/navigation.php");?>
<div class="container" style="width: 500px;">
  <form id="friend_message" action="message_validation.php" method="POST">
    <label>Name: </label>
    <input type="text" name="name" id="name" class="form-control">
    <br>
    <label>Message: </label>
    <input type="text" name="message" id="message" class="form-control">
    <br>
    <input type="submit" name="submit_message" id="submit_message" class="btn btn-info" value="Submit">
    <span id="error_message" class="text-danger"></span>  
    <span id="success_message" class="text-success"></span>
  </form>

</div>

 <script>  
 $(document).ready(function(){  
      $('#submit_message').click(function(){  
           var name = $('#name').val();  
           var message = $('#message').val();  
           if(name == '' || message == '')  
           {  
                $('#error_message').html("All Fields are required");  
           }  
           else  
           {  
                $('#error_message').html('');  
                $.ajax({  
                     url:"insert.php",  
                     method:"POST",  
                     data:{name:name, message:message},  
                     success:function(data){  
                          $("form").trigger("reset");  
                          $('#success_message').fadeIn().html(data);  
                          setTimeout(function(){  
                               $('#success_message').fadeOut("Slow");  
                          }, 2000);  
                     }  
                });  
           }  
      });  
 });  
 </script> 


 