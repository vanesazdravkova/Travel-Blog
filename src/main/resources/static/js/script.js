$(document).ready(function(){
    // Function to change the main image
    function changeMainImage(src, pictureId, canNotDelete) {
        $('#mainImage').attr('src', src);

        // Check if pictureId is not null or undefined before setting the value
        if (pictureId !== null && pictureId !== undefined) {
            $('#mainPictureId').val(pictureId); // Use .val() to set the value
        }

        var deleteButton = $('#deleteImg');

        console.log('pictureId:', pictureId);

        deleteButton.css('display', canNotDelete ? 'none' : 'block');
    }

    function updateMainImageOnClick(element) {
        var src = $(element).attr('src');
        var pictureId = $(element).data('picture-id');
        var canNotDelete = $(element).data('can-not-delete');
        changeMainImage(src, pictureId, canNotDelete);
    }

    // Set the initial image to the first small image
    var initialImage = $('.more-images img:first').attr('src');
    var initialPictureId = $('.more-images img:first').data('picture-id');
    var initialCanNotDelete = $('.more-images img:first').data('can-not-delete');
    changeMainImage(initialImage, initialPictureId, initialCanNotDelete);

    // Clicking on small images should change the main image
    $('.more-images img').click(function(){
        updateMainImageOnClick(this);
    });

    // Manual navigation buttons
    $('#prevButton').click(function(){
        var currentImage = $('#mainImage').attr('src');
        var prevImage = $('.more-images img[src="'+currentImage+'"]').parent().prev().find('img');
        if (prevImage.length > 0) {
            updateMainImageOnClick(prevImage);
        }
    });

    $('#nextButton').click(function(){
        var currentImage = $('#mainImage').attr('src');
        var nextImage = $('.more-images img[src="'+currentImage+'"]').parent().next().find('img');
        if (nextImage.length > 0) {
            updateMainImageOnClick(nextImage);
        }
    });

    // Add an event listener for form submission
    $('#deleteMainImageForm').submit(function(event) {
        // Show the overlay and spinner
        document.getElementById('overlay').style.display = 'flex';
        document.getElementById('spinner').style.display = 'block';

        var deleteButton = event.target.querySelector('input[type="submit"]');
        deleteButton.disabled = true;
    });

});