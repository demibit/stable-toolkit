import { capitalize } from "@mui/material";

export const handleChange = (e, set) => {
  e.preventDefault();
  set(e.target.value);
};

export const displayTag = (tagCode) => {
  return capitalize(tagCode);
};

export const isImageLoaded = (image) => {
  return image.height && image.width && image.fileName && image.location;
};

export const isNullOrUndefined = (obj) => {
  return obj === null || obj === undefined;
};
