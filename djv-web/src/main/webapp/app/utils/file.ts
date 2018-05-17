export const dataUriToFile = (dataURI: string, old: File) => {
    const byteString: string =
        dataURI.split(",")[0]
               .indexOf("base64") >= 0
        ? atob(dataURI.split(",")[1])
        : unescape(dataURI.split(",")[1]);

    // separate out the mime component
    const mimeString = dataURI.split(",")[0]
                              .split(":")[1]
                              .split(";")[0];

    // write the bytes of the string to a typed array
    const ia = new Uint8Array(byteString.length);
    for (let i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }

    return new File([ia], old.name, {type:mimeString, lastModified: old.lastModified});
};

export const blobToFile = (data: Blob, old: File) =>
    new File([data], old.name, {type:old.type, lastModified: old.lastModified});
