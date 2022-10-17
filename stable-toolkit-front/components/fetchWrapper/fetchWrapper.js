const defaultHeaders = {
  "Content-type": "application/json",
  Accept: "application/json",
  "Access-Control-Allow-Origin": "*",
};

const get = async (url) => {
  try {
    const requestOptions = {
      headers: defaultHeaders,
      method: "GET",
    };

    const rawResponse = await fetch(url, requestOptions).catch((e) => null);

    if (rawResponse === null || rawResponse.status !== 200) {
      return null;
    } else {
      return await rawResponse.json();
    }
  } catch (error) {
    return {responseBody: null, responseStatus: 418};
  }
};

const post = async (url, requestBody) => {
  try {
    const requestOptions = {
      headers: defaultHeaders,
      method: "POST",
      body: JSON.stringify(requestBody),
    };

    const rawResponse = await fetch(url, requestOptions).catch((e) => null);

    if (
        rawResponse !== null &&
        (rawResponse.status === 200 || rawResponse.status === 201)
    ) {
      return await rawResponse.json();
    } else {
      return null;
    }
  } catch (error) {
    return {responseBody: null, responseStatus: 418};
  }
};


const put = async (url, requestBody) => {
  try {
    const requestOptions = {
      headers: defaultHeaders,
      method: "PUT",
      body: JSON.stringify(requestBody),
    };

    const rawResponse = await fetch(url, requestOptions).catch((e) => null);

    if (
        rawResponse !== null &&
        (rawResponse.status === 200 || rawResponse.status === 201)
    ) {
      return await rawResponse.json();
    } else {
      return null;
    }
  } catch (error) {
    return {responseBody: null, responseStatus: 418};
  }
};

const delete_ = async (url, requestBody) => {
  try {
    const requestOptions = {
      headers: defaultHeaders,
      method: "DELETE",
      body: JSON.stringify(requestBody)
    };

    const rawResponse = await fetch(url, requestOptions).catch((e) => null);

    if (
        rawResponse !== null &&
        (rawResponse.status === 200 || rawResponse.status === 201)
    ) {
      return await rawResponse.json();
    } else {
      return null;
    }
  } catch (error) {
    return {responseBody: null, responseStatus: 418};
  }
};

export const fetchWrapper = {
  get,
  post,
  put,
  delete: delete_,
};
