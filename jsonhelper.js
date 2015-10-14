function getImageUrl(json)
{
    var myArr = JSON.parse(json);
    return myArr["responseData"]["results"][0]["unescapedUrl"];
}

