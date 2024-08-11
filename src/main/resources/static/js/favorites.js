const favoritesButton = document.getElementById('addOrRemoveButton');
favoritesButton.addEventListener('click', handleFavoritesButtonClick);

async function handleFavoritesButtonClick(event) {
    event.preventDefault();

    const fetchOptions = {
        method: 'POST',
        headers: {
            [csrfHeaderName]: csrfHeaderValue,
            'Content-Type': 'application/json',
            'Accept': 'application/json',
        },
    };

    try {
        const response = await fetch(`/api/trips/${tripId}/addOrRemoveFromFavorites`, fetchOptions);

        if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(errorMessage);
        }

        const isFavorite = await response.json();

        const buttonText = isFavorite ? 'Remove from Favorites' : 'Add to Favorites';
        const iconClass = isFavorite ? 'fas fa-heart-circle-minus' : 'fas fa-heart-circle-plus';

        document.getElementById('favoriteIcon').className = iconClass;
        document.getElementById('favoriteText').innerText = buttonText;

    } catch (error) {
        console.error('Error:', error);
    }
}