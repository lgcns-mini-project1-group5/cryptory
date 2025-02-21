import React, { useEffect, useRef } from "react";
import * as d3 from "d3";

// 예제 데이터 (날짜, 시가, 종가, 고가, 저가)
const data = [
    { date: "2025-02-14", open: 150000, close: 155000, high: 156000, low: 149000 },
    { date: "2025-02-15", open: 155000, close: 152000, high: 157000, low: 151000 },
    { date: "2025-02-16", open: 152000, close: 153000, high: 155500, low: 151500 },
    { date: "2025-02-17", open: 153000, close: 154500, high: 156500, low: 152500 },
    { date: "2025-02-18", open: 154500, close: 157000, high: 158000, low: 154000 },
];

// 특정 날짜에 마커 표시
const markers = {
    "2025-02-16": { color: "purple", icon: "⭐" },
    "2025-02-18": { color: "black", icon: "⚡" }
};

export default function CandleChartCell(props) {
    const svgRef = useRef();

    useEffect(() => {
        const width = 600;
        const height = 400;
        const margin = { top: 20, right: 50, bottom: 50, left: 50 };

        // 기존 SVG 제거
        d3.select(svgRef.current).selectAll("*").remove();

        // SVG 요소 생성
        const svg = d3.select(svgRef.current)
            .attr("width", width)
            .attr("height", height)
            .append("g")
            .attr("transform", `translate(${margin.left}, ${margin.top})`);

        // X축 & Y축 스케일 설정
        const xScale = d3.scaleBand()
            .domain(data.map(d => d.date))
            .range([0, width - margin.left - margin.right])
            .padding(0.2);

        const yScale = d3.scaleLinear()
            .domain([d3.min(data, d => d.low) - 1000, d3.max(data, d => d.high) + 1000])
            .range([height - margin.top - margin.bottom, 0]);

        // X축 추가
        svg.append("g")
            .attr("transform", `translate(0, ${height - margin.top - margin.bottom})`)
            .call(d3.axisBottom(xScale))
            .selectAll("text")
            .attr("transform", "rotate(-30)")
            .style("text-anchor", "end");

        // Y축 추가
        svg.append("g")
            .call(d3.axisLeft(yScale));

        // 캔들 스틱 (고가 - 저가)
        svg.selectAll(".candle-line")
            .data(data)
            .enter()
            .append("line")
            .attr("class", "candle-line")
            .attr("x1", d => xScale(d.date) + xScale.bandwidth() / 2)
            .attr("x2", d => xScale(d.date) + xScale.bandwidth() / 2)
            .attr("y1", d => yScale(d.high))
            .attr("y2", d => yScale(d.low))
            .attr("stroke", "black");

        // 캔들 바 (시가 & 종가)
        svg.selectAll(".candle-bar")
            .data(data)
            .enter()
            .append("rect")
            .attr("class", "candle-bar")
            .attr("x", d => xScale(d.date))
            .attr("y", d => yScale(Math.max(d.open, d.close)))
            .attr("width", xScale.bandwidth())
            .attr("height", d => Math.abs(yScale(d.open) - yScale(d.close)))
            .attr("fill", d => d.open > d.close ? "red" : "blue");

        // 마커 추가
        Object.keys(markers).forEach(date => {
            const marker = markers[date];
            const dataPoint = data.find(d => d.date === date);

            if (dataPoint) {
                svg.append("text")
                    .attr("x", xScale(dataPoint.date) + xScale.bandwidth() / 2)
                    .attr("y", yScale(dataPoint.high) - 10)
                    .attr("text-anchor", "middle")
                    .attr("font-size", "16px")
                    .attr("fill", marker.color)
                    .text(marker.icon);
            }
        });

        // 줌 기능 추가
        const zoom = d3.zoom()
            .scaleExtent([1, 3])
            .translateExtent([[0, 0], [width, height]])
            .on("zoom", (event) => {
                svg.attr("transform", event.transform);
            });

        d3.select(svgRef.current).call(zoom);

    }, []);

    return <svg ref={svgRef}></svg>;
}