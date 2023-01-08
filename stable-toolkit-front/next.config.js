/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  images: {
    loader: 'akamai',
    path: '',
    domains: ["localhost"],
  },
  env: {
    HOSTPATH: "http://localhost:8800",
  },
};

module.exports = nextConfig;
