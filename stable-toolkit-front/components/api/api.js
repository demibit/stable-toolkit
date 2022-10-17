// Next.js API route support: https://nextjs.org/docs/api-routes/introduction
import {fetchWrapper} from "../fetchWrapper/fetchWrapper";

const getImageFilter = async () => {
  const response = await fetchWrapper.get(
      `${process.env.LOCALHOST8080}/image/filter`
  );

  return response;
};

const postImageFilter = async (imageFilter) => {
  const response = await fetchWrapper.post(
      `${process.env.LOCALHOST8080}/image/filter`, imageFilter
  );

  return response;
};

const putImage = async (images) => {
  const response = await fetchWrapper.put(
      `${process.env.LOCALHOST8080}/image`, images
  );

  return response;
};

const getTag = async () => {
  const response = await fetchWrapper.get(
      `${process.env.LOCALHOST8080}/tag`
  );

  return response;
};

const putTag = async (tags) => {
  const response = await fetchWrapper.put(
      `${process.env.LOCALHOST8080}/tag`, tags
  );

  return response;
};

const deleteTag = async (tags) => {
  const response = await fetchWrapper.delete(
      `${process.env.LOCALHOST8080}/tag`, tags
  );

  return response;
};

const getFolder = async () => {
  const response = await fetchWrapper.get(
      `${process.env.LOCALHOST8080}/folder`
  );

  return response;
};

const putFolder = async (folders) => {
  const response = await fetchWrapper.put(
      `${process.env.LOCALHOST8080}/folder`, folders
  );

  return response;
};

const deleteFolder = async (folders) => {
  const response = await fetchWrapper.delete(
      `${process.env.LOCALHOST8080}/folder`, folders
  );

  return response;
};

const getSettings = async () => {
  const response = await fetchWrapper.get(
      `${process.env.LOCALHOST8080}/setting`
  );

  return response;
};

const putSetting = async (settings) => {
  const response = await fetchWrapper.put(
      `${process.env.LOCALHOST8080}/setting`, settings
  );

  return response;
};

const deleteSetting = async (settings) => {
  const response = await fetchWrapper.delete(
      `${process.env.LOCALHOST8080}/setting`, settings
  );

  return response;
};

const triggerReIndex = async () => {
  const response = await fetchWrapper.delete(
      `${process.env.LOCALHOST8080}/image`
  );

  return response;
}

export const api = {
  getImageFilter,
  postImageFilter,
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
