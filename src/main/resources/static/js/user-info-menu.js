let stringToColor = function stringToColor(str) {
    let hash = 0;
    let color = '#';
    let i;
    let value;
    let strLength;

    if (!str) {
        return color + '333333';
    }

    strLength = str.length;

    for (i = 0; i < strLength; i++) {
        hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }

    for (i = 0; i < 3; i++) {
        value = (hash >> (i * 8)) & 0xFF;
        color += ('00' + value.toString(16)).substr(-2);
    }

    return color;
};

if ($("#reguserid").val()) {
    name = document.getElementById('name').innerText;
    letter = name.substr(0, 1);
    backgroundColor = stringToColor(name);
    elementAvatar = document.getElementById('avatar');
    elementName = document.getElementById('name');
    elementName.innerHTML = name;
    if (elementAvatar.innerHTML.length === 0) {
        elementAvatar.innerHTML = letter;
        elementAvatar.style.backgroundColor = backgroundColor;
    }
};
