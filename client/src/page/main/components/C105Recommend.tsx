import { FaBookOpenReader } from "react-icons/fa6";

const C105Recommend = () => {
  return <div>
    <div className="flex flex-row items-center">
      <FaBookOpenReader color="#5F6F52" size={20} className="mr-2" />
      <span>C105가 추천하는 오늘의 도서!</span>
    </div>
    <div
        className="flex overflow-x-auto whitespace-nowrap"
        style={{
          scrollbarWidth: "none",
          msOverflowStyle: "none",
        }}
      >
        {Array(10)
          .fill(null)
          .map((index) => {
            return (
              <img src="harrypotter.png" alt="" width={80} className="mx-1 my-3" />
            );
          })}
      </div>
  </div>
}

export default C105Recommend;