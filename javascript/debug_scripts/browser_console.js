/**
*
*   Load jQuery selectors in console
*   stackoverflow.com/questions/7474354/include-jquery-in-the-javascript-console
***/

var script = document.createElement('script');
script.src = "https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js";
document.getElementsByTagName('head')[0].appendChild(script);






/**
*
*   Print Angular model for each element in a collection
*
***/

$.each( 
  $( "ul" ).find(".media-body"), 
  function( index, element ){
    console.log(
         angular.element(element).scope().dish
    );
  }
);





/**
*
*   Change the background color for each selected element
*
***/

$.each( 
  $( "ul" ).find(".media-body"), 
  function( index, element ){
     $(element).css(
        "background-color", "red"
     )
  }
)