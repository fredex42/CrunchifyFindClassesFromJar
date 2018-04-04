#!/usr/bin/ruby

require 'JSON'

$parsedData = JSON.parse(STDIN.read)

$parsedData['List of Class'].each do |entry|
  puts "<logger name=\"#{entry}\" level=\"INFO\"/>"
end