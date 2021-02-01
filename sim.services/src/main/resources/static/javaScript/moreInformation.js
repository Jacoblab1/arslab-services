$(function(){
    $('.showMoreButton').click(function(event){
        console.log("here");
        event.stopPropagation();
        var name = event.currentTarget.getAttribute('name');
        console.log(name);
        $("#" + name + "Information").toggle();
        $("#" + name + "SVG").toggleClass("flipSVG");
    });
});