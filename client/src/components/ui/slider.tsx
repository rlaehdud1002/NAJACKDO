import * as SliderPrimitive from "@radix-ui/react-slider";
import * as React from "react";

import { cn } from "lib/utils";

const Slider = React.forwardRef<
  React.ElementRef<typeof SliderPrimitive.Root>,
  React.ComponentPropsWithoutRef<typeof SliderPrimitive.Root>
>(({ className, ...props }, ref) => (
  <SliderPrimitive.Root
    ref={ref}
    className={cn(
      "relative flex w-4/5 mx-auto mt-10 touch-none select-none items-center",
      className
    )}
    {...props}
  >
    <SliderPrimitive.Track className="relative h-1.5 w-full grow overflow-hidden rounded-full bg-white/50">
      <SliderPrimitive.Range className="absolute h-full bg-[#79AC78]" />
    </SliderPrimitive.Track>
    <SliderPrimitive.Thumb className="relative flex items-center justify-center h-3 w-3 rounded-full border border-[#79AC78]/50 bg-[#79AC78] shadow transition-colors disabled:pointer-events-none disabled:opacity-50">
      <div className="absolute h-5 w-5 rounded-full bg-[#79AC78]/70 transition-colors disabled:pointer-events-none disabled:opacity-50 blur-sm" />
    </SliderPrimitive.Thumb>
  </SliderPrimitive.Root>
));
Slider.displayName = SliderPrimitive.Root.displayName;

export { Slider };