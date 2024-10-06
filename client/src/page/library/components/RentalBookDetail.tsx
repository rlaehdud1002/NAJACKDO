import CenterCropImage from "page/library/components/CenterCropImage";
import { IoChevronBack } from "react-icons/io5";
import { useNavigate, useParams } from "react-router-dom";
import {
  Carousel,
  CarouselContent,
  CarouselItem,
} from "components/ui/carousel";
import { RxDotFilled, RxDot } from "react-icons/rx";

//<RxDotFilled />
// <RxDot />



interface BookInfoProps {
  frontimageUrl: string;
  backimageUrl: string;
}

const RentalBookDetail = ({ frontimageUrl, backimageUrl }: BookInfoProps) => {
  const { bookId } = useParams();
  const navigate = useNavigate();
  const bookIdAsNumber = parseInt(bookId, 10);
  console.log("frontimageUrl", frontimageUrl);
  console.log("backimageUrl", backimageUrl);

  return (
    <div>
      <div className="relative w-full h-72 object-cover">
        <div
          onClick={() => navigate(-1)}
          className="cursor-pointer absolute left-0 top-0 z-10 p-4"
        >
          <IoChevronBack size={25} color="#79AC78" />
        </div>
        <Carousel 
          opts={{
            align: "start",
            loop: true,
          }}
          className="w-full h-80">
            <CarouselContent>
              <CarouselItem >
                <div className="relative w-full h-72 object-cover">
                  <CenterCropImage imageUrl={frontimageUrl} />
                  <div className="absolute inset-0 flex items-center justify-center">
                    <img src={frontimageUrl} alt="도서 이미지" width={180} className="z-20" />
                  </div>
                  <div className="flex flex-row justify-center absolute bottom-0  -inset-x-1/2">
                    <RxDotFilled />
                    <RxDot />
                  </div>
                </div>
              </CarouselItem>
              <CarouselItem>
                <div className="relative w-full h-72 object-cover">
                  <CenterCropImage imageUrl={backimageUrl} />
                  <div className="absolute inset-0 flex items-center justify-center">
                    <img src={backimageUrl} alt="도서 이미지" width={180} className="z-20" />
                  </div>
                  <div className="flex flex-row justify-center absolute bottom-0  -inset-x-1/2">
                    <RxDot />
                    <RxDotFilled />
                  </div>
                </div>
              </CarouselItem>
            </CarouselContent>
          </Carousel>
      </div>
    </div>
  );
};

export default RentalBookDetail;
