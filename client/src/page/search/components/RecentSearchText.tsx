import { IoMdTime } from "react-icons/io";
import { PiXBold } from "react-icons/pi";

const RecentSearchText = () => {
  return (
    <div className="mx-2 my-4 flex flex-row justify-between">
      <div className="flex flex-row">
        <IoMdTime size={25} color="#545454" />
        <span className="ml-3">search text</span>
      </div>
      <PiXBold color="#545454" />
    </div>
  );
};

export default RecentSearchText;