import {fetchWrapper} from "../fetchWrapper/fetchWrapper";

const getImageFilter = async () => {
  const response = await fetchWrapper.get(
      `${process.env.HOSTPATH}/image/filter`
  );

  return response;
};

const postImageFilter = async (imageFilter) => {
  const response = await fetchWrapper.post(
      `${process.env.HOSTPATH}/image/filter`, imageFilter
  );

  return response;
};

const findImage = async (path) => {
  const response = await fetchWrapper.get(
      `${process.env.HOSTPATH}/image/find?path=${path}`
  );

  return response;
}

const putImage = async (images) => {
  const response = await fetchWrapper.put(
      `${process.env.HOSTPATH}/image`, images
  );

  return response;
};

const getTag = async () => {
  const response = await fetchWrapper.get(
      `${process.env.HOSTPATH}/tag`
  );

  return response;
};

const putTag = async (tags) => {
  const response = await fetchWrapper.put(
      `${process.env.HOSTPATH}/tag`, tags
  );

  return response;
};

const deleteTag = async (tags) => {
  const response = await fetchWrapper.delete(
      `${process.env.HOSTPATH}/tag`, tags
  );

  return response;
};

const getFolder = async () => {
  const response = await fetchWrapper.get(
      `${process.env.HOSTPATH}/folder`
  );

  return response;
};

const putFolder = async (folders) => {
  const response = await fetchWrapper.put(
      `${process.env.HOSTPATH}/folder`, folders
  );

  return response;
};

const deleteFolder = async (folders) => {
  const response = await fetchWrapper.delete(
      `${process.env.HOSTPATH}/folder`, folders
  );

  return response;
};

const getSettings = async () => {
  const response = await fetchWrapper.get(
      `${process.env.HOSTPATH}/setting`
  );

  return response;
};

const putSetting = async (settings) => {
  const response = await fetchWrapper.put(
      `${process.env.HOSTPATH}/setting`, settings
  );

  return response;
};

const deleteSetting = async (settings) => {
  const response = await fetchWrapper.delete(
      `${process.env.HOSTPATH}/setting`, settings
  );

  return response;
};

const triggerReIndex = async () => {
  const response = await fetchWrapper.delete(
      `${process.env.HOSTPATH}/image`
  );

  return response;
}

export const api = {
  getImageFilter,
  postImageFilter,
  findImage,
  putImage,
  getTag,
  putTag,
  deleteTag,
  getFolder,
  putFolder,
  deleteFolder,
  getSettings,
  putSetting,
  deleteSetting,
  triggerReIndex
};
